package moduloAnexos.service.impl;

import moduloAnexos.autenticacao.JwtTokenProvider;
import moduloAnexos.excecoes.MsgGenericaParametrizadaEnum;
import moduloAnexos.excecoes.MsgGenericaParametrizadaException;
import moduloAnexos.model.AnexosSolicitacaoServico;
import moduloAnexos.repository.AnexosSolicitacaoServicoRepository;
import moduloAnexos.service.AnexosAgenciaSolicitacaoServicoService;
import moduloAnexos.service.AuditoriaService;
import moduloAnexos.service.dto.AnexosSolicitacaoServicoDTO;
import moduloAnexos.service.dto.AnexosSolicitacaoServicoWrapperDTO;
import moduloAnexos.service.dto.DownloadArquivoDTO;
import moduloAnexos.service.mapper.AnexosSolicitacaoServicoMapper;
import moduloAnexos.service.validacoes.AnexosAgenciaSolicitacaoServicoValidacoes;
import moduloAnexos.service.validacoes.ValidarArquivoAgencia;
import moduloAnexos.util.Constants;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AnexosAgenciaSolicitacaoServicoServiceImpl implements AnexosAgenciaSolicitacaoServicoService {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private AnexosSolicitacaoServicoRepository anexosSolicitacaoServicoRepository;
    @Autowired
    private AnexosAgenciaSolicitacaoServicoValidacoes anexosAgenciaSolicitacaoServicoValidacoes;
    @Autowired
    private AnexosSolicitacaoServicoMapper anexosSolicitacaoServicoMapper;
    @Autowired
    private AuditoriaService auditoriaService;

    @Override
    public AnexosSolicitacaoServicoWrapperDTO buscarPorId(Integer cdAtendimento, String refAtendimentoFormatada, Short seqSS, Pageable pageable) {
        Integer refAtendimento = Integer.parseInt("20" + refAtendimentoFormatada.substring(3, 5) + refAtendimentoFormatada.substring(0, 2));
        AnexosSolicitacaoServicoWrapperDTO listAnexoSSWrapperDTO = new AnexosSolicitacaoServicoWrapperDTO();
        List<AnexosSolicitacaoServicoDTO> listAnexoBaixarSSDTO = this.anexosSolicitacaoServicoRepository.buscarPorSS(cdAtendimento, refAtendimento, seqSS, pageable);
        Integer totalArquivo = this.anexosSolicitacaoServicoRepository.buscarPorSS(cdAtendimento, refAtendimento, seqSS).stream().mapToInt(e -> e.getTamanhoArquivo()).sum();
        long totalRegistro = this.anexosSolicitacaoServicoRepository.countByCodAtendimentoAndRefAtendimentoAndSeqSS(cdAtendimento, refAtendimento, seqSS);

        listAnexoSSWrapperDTO.setListAnexosSolicitacaoServicoDTO(listAnexoBaixarSSDTO);
        listAnexoSSWrapperDTO.setTotalRegistro(totalRegistro);
        listAnexoSSWrapperDTO.setTotalArquivo(totalArquivo);
        return listAnexoSSWrapperDTO;
    }

    @Override
    public AnexosSolicitacaoServicoDTO salvar(AnexosSolicitacaoServicoDTO anexosSolicitacaoServicoDTO, String token, AnexosSolicitacaoServico anexosSolicitacaoServico, int limiteMegaArquivo, int tamanhoMaximoArquivo, List<String> extensoesInvalidas){
        if(!this.anexosAgenciaSolicitacaoServicoValidacoes.validarSS(anexosSolicitacaoServicoDTO)) {
            throw new MsgGenericaParametrizadaException(MsgGenericaParametrizadaEnum.SS_NAO_ENCONTRADA).setStatusCode(404);
        }

        byte[] arquivo= Base64.decodeBase64(anexosSolicitacaoServicoDTO.getData());

        Integer tamanhoArquivos = anexosSolicitacaoServicoRepository.buscarPorSS(anexosSolicitacaoServicoDTO.getCodAtendimento(), anexosSolicitacaoServicoDTO.getRefAtendimento(),anexosSolicitacaoServicoDTO.getSeqSS()).stream().mapToInt(e -> e.getTamanhoArquivo()).sum() + arquivo.length;

        ValidarArquivoAgencia.validarTotalArquivo(tamanhoArquivos, limiteMegaArquivo);
        ValidarArquivoAgencia.validarExtensaoArquivo(anexosSolicitacaoServicoDTO.getNomeArquivo(), arquivo, extensoesInvalidas);
        ValidarArquivoAgencia.validarTamanhoArquivo(arquivo.length, tamanhoMaximoArquivo);

        anexosSolicitacaoServico.setArquivo(arquivo);
        anexosSolicitacaoServico.setNomeArquivo(anexosSolicitacaoServicoDTO.getNomeArquivo());
        anexosSolicitacaoServico.setTamanhoArquivo(arquivo.length);
        anexosSolicitacaoServico.setCodAtendimento(anexosSolicitacaoServicoDTO.getCodAtendimento());
        anexosSolicitacaoServico.setRefAtendimento(anexosSolicitacaoServicoDTO.getRefAtendimento());
        anexosSolicitacaoServico.setSeqSS(anexosSolicitacaoServicoDTO.getSeqSS());
        anexosSolicitacaoServico.setDescricao(anexosSolicitacaoServicoDTO.getDescricao());
        anexosSolicitacaoServico.setUsuario(anexosSolicitacaoServicoDTO.getUsuario());
        anexosSolicitacaoServicoRepository.save(anexosSolicitacaoServico);

        String anexosSolicitacaoServicoJson = this.anexosSolicitacaoServicoMapper.toDto(anexosSolicitacaoServico).toJson();

        this.auditoriaService.gerarAuditoria(anexosSolicitacaoServico.getId().longValue(), Constants.EMPTY_STRING, anexosSolicitacaoServicoJson, 30L,"Anexos SS", this.jwtTokenProvider.buscarIdUsuario(token));

        return anexosSolicitacaoServicoMapper.toDto(anexosSolicitacaoServico);
    }

    @Override
    public DownloadArquivoDTO buscarArquivoPorId(Long id) {
        Optional<AnexosSolicitacaoServico> anexosSolicitacaoServico = anexosSolicitacaoServicoRepository.findById(id);
        DownloadArquivoDTO downloadArquivoDTO = new DownloadArquivoDTO();
        this.anexosAgenciaSolicitacaoServicoValidacoes.verificarExistenciaSS(anexosSolicitacaoServico);
        downloadArquivoDTO.setData(Base64.encodeBase64String(anexosSolicitacaoServico.get().getArquivo()));
        downloadArquivoDTO.setNomeArquivo(anexosSolicitacaoServico.get().getNomeArquivo());
        return downloadArquivoDTO;
    }
}
