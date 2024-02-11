package moduloAnexos.service;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import moduloAnexos.service.dto.AnexosArquivoBeneficiarioDTO;
import moduloAnexos.service.dto.AnexosBeneficiarioDTO;
import moduloAnexos.service.dto.AnexosBeneficiarioWrapperDTO;
import moduloAnexos.service.dto.DownloadArquivoDTO;

public interface AnexosBeneficiarioService {

	public AnexosBeneficiarioDTO salvar(AnexosBeneficiarioDTO anexosBeneficiarioDTO, String token);

	public void excluir(Long id, String token);

	public AnexosBeneficiarioDTO buscarPorId(Long id);

	public AnexosBeneficiarioWrapperDTO buscarPorCodigoBeneficiario(String cdCliente, Pageable pageable);

	public AnexosArquivoBeneficiarioDTO salvarArquivo(MultipartFile file);

	public DownloadArquivoDTO buscarArquivoPorId(Long id);

}
