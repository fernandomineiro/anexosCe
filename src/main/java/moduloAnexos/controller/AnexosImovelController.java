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

import moduloAnexos.service.AnexosImovelService;
import moduloAnexos.service.dto.AnexosArquivoImovelDTO;
import moduloAnexos.service.dto.AnexosImovelDTO;
import moduloAnexos.service.dto.AnexosImovelWrapperDTO;
import moduloAnexos.service.dto.DownloadArquivoDTO;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping("/backend-anexosImovel/anexos")
public class AnexosImovelController {

	@Autowired
	private AnexosImovelService anexosImovelService;

	@CrossOrigin
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public AnexosImovelDTO cadastrar(@RequestBody AnexosImovelDTO anexosImovelDTO,
			@RequestHeader("Authorization") String token) {
		return anexosImovelService.salvar(anexosImovelDTO, token);

	}

	@CrossOrigin
	@DeleteMapping("/{id}")
	public void excluir(@PathVariable(value = "id") Long id, @RequestHeader("Authorization") String token) {
		anexosImovelService.excluir(id, token);
	}

	@CrossOrigin
	@GetMapping("/{matriculaImovel}")
	public AnexosImovelWrapperDTO buscarPorMatriculaImovel(
			@PathVariable(value = "matriculaImovel") Integer matriculaImovel, Pageable pageable) {
		return anexosImovelService.buscarPorMatriculaImovel(matriculaImovel, pageable);
	}

	@CrossOrigin
	@PostMapping("/uploadArquivo")
	public AnexosArquivoImovelDTO salvarArquivo(@RequestParam("arquivo") MultipartFile arquivo) {
		
		return anexosImovelService.salvarArquivo(arquivo);
	}
	
	 @CrossOrigin
	 @GetMapping("/downloadArquivo/{id}")
	    public DownloadArquivoDTO downloadArquivo(@PathVariable Long id,HttpServletResponse response) {
		  return anexosImovelService.buscarArquivoPorId(id);
	        
	    }
}
