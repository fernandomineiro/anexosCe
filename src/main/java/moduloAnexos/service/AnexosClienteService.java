package moduloAnexos.service;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import moduloAnexos.service.dto.AnexosArquivoClienteDTO;
import moduloAnexos.service.dto.AnexosClienteDTO;
import moduloAnexos.service.dto.AnexosClienteWrapperDTO;
import moduloAnexos.service.dto.DownloadArquivoDTO;

public interface AnexosClienteService {

	public AnexosClienteDTO salvar(AnexosClienteDTO anexosClienteDTO, String token);

	public void excluir(Long id, String token);

	public AnexosClienteDTO buscarPorId(Long id);

	public AnexosClienteWrapperDTO buscarPorCodigoCliente(String cdCliente, Pageable pageable);

	public AnexosArquivoClienteDTO salvarArquivo(MultipartFile file);

	public DownloadArquivoDTO buscarArquivoPorId(Long id);

}
