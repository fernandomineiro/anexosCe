package moduloAnexos.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import moduloAnexos.autenticacao.JwtTokenProvider;
import moduloAnexos.excecoes.MsgGenericaPersonalizadaException;
import moduloAnexos.service.AnexosOshService;
import moduloAnexos.service.AnexosSolicitacaoServicoService;
import moduloAnexos.service.dto.AnexosOshDTO;
import moduloAnexos.service.dto.AnexosSolicitacaoServicoDTO;
import moduloAnexos.service.dto.SolicitacaoServicoDTO;
import moduloAnexos.service.dto.UploadArquivoDTO;
import moduloAnexos.util.UrlMicroservico;

@Service
@Transactional
public class AnexosOshServiceImpl implements AnexosOshService {

	@Autowired
	private UrlMicroservico urlMicroservico;
	@Autowired
	private AnexosSolicitacaoServicoService anexosSolicitacaoServicoService;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Override
	public void salvar(AnexosOshDTO anexosOshDTO, String token) {
	
		SolicitacaoServicoDTO ssDTO=this.validarSS(anexosOshDTO); 
		if(ssDTO.isFlagExclusao())
			 throw new MsgGenericaPersonalizadaException("Essa SS foi excluída.Não pode ser incluído um anexo");
		
		anexosOshDTO.getListUploadArquivoDTO().forEach(e->this.salvar(e, ssDTO, token));
	}
	
	private void salvar(UploadArquivoDTO uploadArquivoDTO, SolicitacaoServicoDTO ssDTO, String token) {
		anexosSolicitacaoServicoService.salvar(
				this.toAnexosSolicitacaoServicoDTO(uploadArquivoDTO, ssDTO, jwtTokenProvider.buscarLogin(token)),
				jwtTokenProvider.buscarIdUsuario(token));
	}
	
	private SolicitacaoServicoDTO validarSS(AnexosOshDTO anexosOshDTO) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		RestTemplate restTemplate = new RestTemplate();
		String url = urlMicroservico.getUrlServico() + "/backend-servico/ordemServicoHidrometro/solicitacaoServico";
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("refOsh", anexosOshDTO.getRefOsh())
				.queryParam("numOsh", anexosOshDTO.getNumOsh()).queryParam("matricula", anexosOshDTO.getMatricula());
		HttpEntity<?> request = new HttpEntity<>(headers);
		 try {
		HttpEntity<SolicitacaoServicoDTO> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, request,
				SolicitacaoServicoDTO.class);
		return response.getBody();
		 }catch ( HttpClientErrorException httpClientErrorException) {
		        throw new MsgGenericaPersonalizadaException("SS não existe");
		  }
		
		
	}
	
	private AnexosSolicitacaoServicoDTO toAnexosSolicitacaoServicoDTO(UploadArquivoDTO uploadArquivoDTO, SolicitacaoServicoDTO ssDTO,String usuario) {
		AnexosSolicitacaoServicoDTO anexosSolicitacaoServicoDTO = new AnexosSolicitacaoServicoDTO();
		anexosSolicitacaoServicoDTO.setNomeArquivo(uploadArquivoDTO.getNomeArquivo());
		anexosSolicitacaoServicoDTO.setData(uploadArquivoDTO.getData());
		anexosSolicitacaoServicoDTO.setRefAtendimento(ssDTO.getRefAtendimento());
		anexosSolicitacaoServicoDTO.setSeqSS(ssDTO.getSeqSS().shortValue());
		anexosSolicitacaoServicoDTO.setCodAtendimento(ssDTO.getCodAtendimento());
		anexosSolicitacaoServicoDTO.setUsuario(usuario);
	
		return anexosSolicitacaoServicoDTO;
	}

}
