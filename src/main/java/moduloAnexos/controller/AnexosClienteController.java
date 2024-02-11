package moduloAnexos.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import moduloAnexos.service.AnexosClienteService;
import moduloAnexos.service.dto.AnexosArquivoClienteDTO;
import moduloAnexos.service.dto.AnexosClienteDTO;
import moduloAnexos.service.dto.AnexosClienteWrapperDTO;
import moduloAnexos.service.dto.DownloadArquivoDTO;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping("/backend-anexosCliente/anexos")
public class AnexosClienteController {

	@Autowired
	private AnexosClienteService anexosClienteService;

	@CrossOrigin
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public AnexosClienteDTO cadastrar(@RequestBody AnexosClienteDTO anexosImovelDTO,
			@RequestHeader("Authorization") String token) {
		return anexosClienteService.salvar(anexosImovelDTO, token);
	}

	@CrossOrigin
	@DeleteMapping("/{id}")
	public void excluir(@PathVariable(value = "id") Long id, @RequestHeader("Authorization") String token) {
		anexosClienteService.excluir(id, token);
	}

	@CrossOrigin
	@GetMapping("/{cdCliente}")
	public AnexosClienteWrapperDTO buscarPorCodigoCliente(@PathVariable(value = "cdCliente") String cdCliente,
			Pageable pageable) {
		return anexosClienteService.buscarPorCodigoCliente(cdCliente, pageable);
	}

	@CrossOrigin
	@PostMapping("/uploadArquivo")
	public AnexosArquivoClienteDTO salvarArquivo(@RequestParam("arquivo") MultipartFile arquivo) {
		return anexosClienteService.salvarArquivo(arquivo);
	}

	@CrossOrigin
	@GetMapping("/downloadArquivo/{id}")
	public DownloadArquivoDTO downloadArquivo(@PathVariable Long id, HttpServletResponse response) {
		return anexosClienteService.buscarArquivoPorId(id);
	}

}
