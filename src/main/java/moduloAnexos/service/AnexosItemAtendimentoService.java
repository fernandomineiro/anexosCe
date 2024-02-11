package moduloAnexos.service;

import org.springframework.data.domain.Pageable;

import moduloAnexos.service.dto.AnexosItemAtendimentoDTO;
import moduloAnexos.service.dto.AnexosItemAtendimentoWrapperDTO;
import moduloAnexos.service.dto.DownloadArquivoDTO;

public interface AnexosItemAtendimentoService {

	public AnexosItemAtendimentoDTO salvar(AnexosItemAtendimentoDTO anexosItemAtendimentoDTO, String token);

	public void excluir(Long id, String token);

	public AnexosItemAtendimentoDTO buscarPorId(Long id);

	public AnexosItemAtendimentoWrapperDTO buscarPorId(Integer cdAtendimento, String refAtendimentoFormatada,
			Short seqSS, Pageable pageable);

	public DownloadArquivoDTO buscarArquivoPorId(Long id);

}
