package moduloAnexos.service;

import moduloAnexos.model.AnexosSolicitacaoServico;
import moduloAnexos.service.dto.AnexosSolicitacaoServicoDTO;
import moduloAnexos.service.dto.AnexosSolicitacaoServicoWrapperDTO;
import moduloAnexos.service.dto.DownloadArquivoDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AnexosAgenciaSolicitacaoServicoService {

    AnexosSolicitacaoServicoWrapperDTO buscarPorId(Integer cdAtendimento, String refAtendimentoFormatada, Short seqSS, Pageable pageable);
    AnexosSolicitacaoServicoDTO salvar(AnexosSolicitacaoServicoDTO anexosSolicitacaoServicoDTO, String token, AnexosSolicitacaoServico anexosSolicitacaoServico, int limiteMegaArquivo, int tamanhoMaximoArquivo, List<String> extensoesInvalidas);
    DownloadArquivoDTO buscarArquivoPorId(Long id);
}
