package moduloAnexos.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import moduloAnexos.service.dto.AnexosBaixarSolicitacaoServicoDTO;
import moduloAnexos.service.dto.AnexosBaixarSolicitacaoServicoWrapperDTO;
import moduloAnexos.service.dto.DownloadArquivoDTO;

public interface AnexosBaixarSolicitacaoServicoService {
	
	public AnexosBaixarSolicitacaoServicoWrapperDTO buscarPorId(Integer cdAtendimento, String refAtendimentoFormatada,Short seqSS, Pageable pageable);
	
	public AnexosBaixarSolicitacaoServicoDTO salvar(AnexosBaixarSolicitacaoServicoDTO anexosBaixarSolicitacaoServicoDTO, String token);
	
	public DownloadArquivoDTO buscarArquivoPorId(Long id);
	
	public void excluir(Long id, String token);
	
	public void excluirPorSs(Integer cdAtendimento, Integer refAtendimento,Short seqSS,Integer idSs,String token);
	
	public List<AnexosBaixarSolicitacaoServicoDTO> buscarPorId(Integer cdAtendimento, String refAtendimentoFormatada,Short seqSS);

	public List<AnexosBaixarSolicitacaoServicoDTO> buscarPorIdAll(Integer cdAtendimento, String refAtendimentoFormatada,
			Short seqSS);
}
