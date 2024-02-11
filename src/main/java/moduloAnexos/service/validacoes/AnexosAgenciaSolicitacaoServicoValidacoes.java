package moduloAnexos.service.validacoes;

import moduloAnexos.excecoes.MsgGenericaParametrizadaEnum;
import moduloAnexos.excecoes.MsgGenericaParametrizadaException;
import moduloAnexos.model.AnexosSolicitacaoServico;
import moduloAnexos.service.dto.AnexosSolicitacaoServicoDTO;
import moduloAnexos.service.dto.SolicitacaoServicoDTO;
import moduloAnexos.util.UrlMicroservico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class AnexosAgenciaSolicitacaoServicoValidacoes {

    @Autowired
    private UrlMicroservico urlMicroservico;

    public boolean validarSS(AnexosSolicitacaoServicoDTO anexosSolicitacaoServicoDTO) {
        String refAtendimento=anexosSolicitacaoServicoDTO.getRefAtendimento().toString().substring(4, 6)+"/"+anexosSolicitacaoServicoDTO.getRefAtendimento().toString().substring(2,4);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        RestTemplate restTemplate = new RestTemplate();
        String url = urlMicroservico.getUrlServico() + "/backend-servico/solicitacaoServico";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("refAtendimento", refAtendimento).queryParam("codAtendimento", anexosSolicitacaoServicoDTO.getCodAtendimento()).queryParam("ssSequencial", anexosSolicitacaoServicoDTO.getSeqSS());
        HttpEntity<?> request = new HttpEntity<>(headers);
        try {
            HttpEntity<SolicitacaoServicoDTO> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, request, SolicitacaoServicoDTO.class);
            if(response.getBody().isFlagExclusao())
                return false;
            else
                return true;
        }catch ( HttpClientErrorException httpClientErrorException) {
            throw new MsgGenericaParametrizadaException(MsgGenericaParametrizadaEnum.SS_NAO_ENCONTRADA).setStatusCode(404);
        }
    }

    public void verificarExistenciaSS(Optional<AnexosSolicitacaoServico> anexosSolicitacaoServico){
        if (anexosSolicitacaoServico.isEmpty()) {
            throw new MsgGenericaParametrizadaException(MsgGenericaParametrizadaEnum.SS_NAO_ENCONTRADA).setStatusCode(404);
        }
    }
}
