package moduloAnexos.service;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import moduloAnexos.service.dto.AnexosArquivoOcorrenciaOperacionalDTO;
import moduloAnexos.service.dto.AnexosOcorrenciaOperacionalDTO;
import moduloAnexos.service.dto.AnexosOcorrenciaOperacionalWrapperDTO;
import moduloAnexos.service.dto.DownloadArquivoDTO;

public interface AnexosOcorrenciaOperacionalService {

	public AnexosOcorrenciaOperacionalDTO salvar(AnexosOcorrenciaOperacionalDTO anexosOcorrenciaOperacionalDTO, String token);

	public void excluir(Long id, String token);

	public AnexosOcorrenciaOperacionalDTO buscarPorId(Long id);

	public AnexosOcorrenciaOperacionalWrapperDTO buscarPorCodigoOcorrenciaOperacional(Long idOcorrenciaOperacional, Pageable pageable);

	public AnexosArquivoOcorrenciaOperacionalDTO salvarArquivo(MultipartFile file, Long idOcorrenciaOperacional);

	public DownloadArquivoDTO buscarArquivoPorId(Long id);

}
