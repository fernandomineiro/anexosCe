package moduloAnexos.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import moduloAnexos.service.dto.AnexosMedicaoSsDTO;
import moduloAnexos.service.dto.AnexosMedicaoSsWrapperDTO;
import moduloAnexos.service.dto.DownloadArquivoDTO;

public interface AnexosMedicaoSsService {

	public void salvar(AnexosMedicaoSsDTO anexosMedicaoSsDTO, String token);
	
	public void atualizar(AnexosMedicaoSsDTO anexosMedicaoSsDTO, String token);
	
	public AnexosMedicaoSsWrapperDTO buscarPorIdSs(Integer cdAtendimento, String refAtendimentoFormatada,Short seqSS, Pageable pageable);
	
	public DownloadArquivoDTO buscarArquivoPorId(Long id,boolean anexoAntigo);
	
	public void excluir(Long id,boolean anexoAntigo, String token);
	
	public AnexosMedicaoSsDTO buscarPorId(Long id,boolean anexoAntigo);
	
	public void excluirTodosPorSs(Long idMedicao,Integer refAtendimento,Integer cdAtendimento,Short seqSS,Integer idSs,String token);
	
	public List<AnexosMedicaoSsDTO> buscarPorIdSs(Integer cdAtendimento, String refAtendimentoFormatada,Short seqSS);
	
	public List<AnexosMedicaoSsDTO> buscarAnexoAntigoPorIdSs(Integer cdAtendimento, String refAtendimentoFormatada,Short seqSS);

	public List<AnexosMedicaoSsDTO> buscarPorIdSsAll(Integer cdAtendimento, String refAtendimentoFormatada,
			Short seqSS);
}
