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

import moduloAnexos.service.AnexosBeneficiarioService;
import moduloAnexos.service.dto.AnexosArquivoBeneficiarioDTO;
import moduloAnexos.service.dto.AnexosBeneficiarioDTO;
import moduloAnexos.service.dto.AnexosBeneficiarioWrapperDTO;
import moduloAnexos.service.dto.DownloadArquivoDTO;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping("/backend-anexosBeneficiario/anexos")
public class AnexosBeneficiarioController {

	@Autowired
	private AnexosBeneficiarioService anexosBeneficiarioService;

	@CrossOrigin
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public AnexosBeneficiarioDTO cadastrar(@RequestBody AnexosBeneficiarioDTO anexosImovelDTO,
			@RequestHeader("Authorization") String token) {
		return anexosBeneficiarioService.salvar(anexosImovelDTO, token);
	}

	@CrossOrigin
	@DeleteMapping("/{id}")
	public void excluir(@PathVariable(value = "id") Long id, @RequestHeader("Authorization") String token) {
		anexosBeneficiarioService.excluir(id, token);
	}

	@CrossOrigin
	@GetMapping("/{cdBeneficiario}")
	public AnexosBeneficiarioWrapperDTO buscarPorCodigoCliente(@PathVariable(value = "cdBeneficiario") String cdBeneficiario,
			Pageable pageable) {
		return anexosBeneficiarioService.buscarPorCodigoBeneficiario(cdBeneficiario, pageable);
	}

	@CrossOrigin
	@PostMapping("/uploadArquivo")
	public AnexosArquivoBeneficiarioDTO salvarArquivo(@RequestParam("arquivo") MultipartFile arquivo) {
		return anexosBeneficiarioService.salvarArquivo(arquivo);
	}

	@CrossOrigin
	@GetMapping("/downloadArquivo/{id}")
	public DownloadArquivoDTO downloadArquivo(@PathVariable Long id, HttpServletResponse response) {
		return anexosBeneficiarioService.buscarArquivoPorId(id);
	}

}
