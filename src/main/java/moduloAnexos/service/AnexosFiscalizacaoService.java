package moduloAnexos.service;

import org.springframework.data.domain.Pageable;

import moduloAnexos.service.dto.AnexosFiscalizacaoDTO;
import moduloAnexos.service.dto.AnexosFiscalizacaoWrapperDTO;
import moduloAnexos.service.dto.DownloadArquivoDTO;

public interface AnexosFiscalizacaoService {

	public AnexosFiscalizacaoDTO salvar(AnexosFiscalizacaoDTO anexosMedicaoSsDTO, String token);
			
	public DownloadArquivoDTO buscarArquivoPorId(Long id);
	
	public void excluir(Long id, String token);
	
	public void excluirTodosPorFiscalizacao(Integer idFiscalizacoesSS);
	
	public AnexosFiscalizacaoWrapperDTO buscarPorIdSs(Integer idFiscalizacoesSS, Pageable pageable);
	
}
