package moduloAnexos.service;

import org.springframework.data.domain.Pageable;

import moduloAnexos.service.dto.AnexosDadosSsDTO;
import moduloAnexos.service.dto.AnexosSsGenericoWrapperDTO;
import moduloAnexos.service.dto.DownloadArquivoDTO;

public interface AnexosDadosSsService {

	public AnexosSsGenericoWrapperDTO buscarIdSs(Integer cdAtendimento, String refAtendimentoFormatada, Short seqSS,
			String token, Pageable pageable);
	
	public AnexosSsGenericoWrapperDTO buscarIdSs2(Integer cdAtendimento, String refAtendimentoFormatada, Short seqSS,
			String token, Pageable pageable);
	
	public DownloadArquivoDTO buscarArquivoPorId(Long id,String origemArquivo);
	
	public void salvar(AnexosDadosSsDTO anexosDadosSsDTO, String token);
	
	public void atualizar(AnexosDadosSsDTO anexosDadosSsDTO, String token);
	
	public void excluir(Long id, String token);
	
	
}
