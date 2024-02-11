package moduloAnexos.service;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import moduloAnexos.service.dto.AnexosArquivoImovelDTO;
import moduloAnexos.service.dto.AnexosImovelDTO;
import moduloAnexos.service.dto.AnexosImovelWrapperDTO;
import moduloAnexos.service.dto.DownloadArquivoDTO;

public interface AnexosImovelService {

	public AnexosImovelDTO salvar(AnexosImovelDTO anexosImovelDTO, String token);

	public void excluir(Long id, String token);

	public AnexosImovelDTO buscarPorId(Long id);

	public AnexosImovelWrapperDTO buscarPorMatriculaImovel(Integer matriculaImovel, Pageable pageable);

	public AnexosArquivoImovelDTO salvarArquivo(MultipartFile file);

	public DownloadArquivoDTO buscarArquivoPorId(Long id);

}
