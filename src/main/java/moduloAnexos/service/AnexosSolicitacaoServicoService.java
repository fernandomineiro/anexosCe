package moduloAnexos.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import moduloAnexos.service.dto.AnexosSolicitacaoServicoDTO;
import moduloAnexos.service.dto.AnexosSolicitacaoServicoWrapperDTO;
import moduloAnexos.service.dto.DownloadArquivoDTO;

public interface AnexosSolicitacaoServicoService {

	public AnexosSolicitacaoServicoDTO salvar(AnexosSolicitacaoServicoDTO anexosSolicitacaoServicoDTO, String token);
	
	public AnexosSolicitacaoServicoDTO salvar(AnexosSolicitacaoServicoDTO anexosSolicitacaoServicoDTO, Long idUsuario);

	public void excluir(Long id, String token);

	public AnexosSolicitacaoServicoDTO buscarPorId(Long id);

	public AnexosSolicitacaoServicoWrapperDTO buscarPorId(Integer cdAtendimento, String refAtendimentoFormatada,Short seqSS, Pageable pageable);
	public AnexosSolicitacaoServicoWrapperDTO buscarPorId(Integer cdAtendimento, Integer refAtendimento,Short seqSS, Pageable pageable);
	
	public List<AnexosSolicitacaoServicoDTO> buscarPorId(Integer cdAtendimento, String refAtendimentoFormatada,Short seqSS);
	
	public List<AnexosSolicitacaoServicoDTO> buscarPorIdAll(Integer cdAtendimento, String refAtendimentoFormatada,
			Short seqSS);
	
	public DownloadArquivoDTO buscarArquivoPorId(Long id);

}
