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

import moduloAnexos.service.AnexosOcorrenciaOperacionalService;
import moduloAnexos.service.dto.AnexosArquivoOcorrenciaOperacionalDTO;
import moduloAnexos.service.dto.AnexosOcorrenciaOperacionalDTO;
import moduloAnexos.service.dto.AnexosOcorrenciaOperacionalWrapperDTO;
import moduloAnexos.service.dto.DownloadArquivoDTO;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping("/backend-anexosOcorrenciaOperacional/anexos")
public class AnexosOcorrenciaOperacionalController {

	@Autowired
	private AnexosOcorrenciaOperacionalService anexosOcorrenciaOperacionalService;

	@CrossOrigin
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public AnexosOcorrenciaOperacionalDTO cadastrar(@RequestBody AnexosOcorrenciaOperacionalDTO anexosOcorrenciaOperacionalDTO,
			@RequestHeader("Authorization") String token) {
		return anexosOcorrenciaOperacionalService.salvar(anexosOcorrenciaOperacionalDTO, token);
	}

	@CrossOrigin
	@DeleteMapping("/{id}")
	public void excluir(@PathVariable(value = "id") Long id, @RequestHeader("Authorization") String token) {
		anexosOcorrenciaOperacionalService.excluir(id, token);
	}

	@CrossOrigin
	@GetMapping("/{idOcorrenciaOperacional}")
	public AnexosOcorrenciaOperacionalWrapperDTO buscarPorCodigoOcorrenciaOperacional(@PathVariable Long idOcorrenciaOperacional, Pageable pageable) {
		return anexosOcorrenciaOperacionalService.buscarPorCodigoOcorrenciaOperacional(idOcorrenciaOperacional, pageable);
	}

	@CrossOrigin
	@PostMapping("/uploadArquivo")
	public AnexosArquivoOcorrenciaOperacionalDTO salvarArquivo(@RequestParam("arquivo") MultipartFile arquivo, Long idOcorrenciaOperacional) {
		return anexosOcorrenciaOperacionalService.salvarArquivo(arquivo, idOcorrenciaOperacional);
	}

	@CrossOrigin
	@GetMapping("/downloadArquivo/{id}")
	public DownloadArquivoDTO downloadArquivo(@PathVariable Long id, HttpServletResponse response) {
		return anexosOcorrenciaOperacionalService.buscarArquivoPorId(id);
	}

}
