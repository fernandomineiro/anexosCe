package moduloAnexos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import moduloAnexos.service.AnexosOshService;
import moduloAnexos.service.dto.AnexosOshDTO;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping("/backend-anexosRetornoOsh/anexos")
public class AnexosRetornoOshController {

	@Autowired
	private AnexosOshService anexosOshService;
	
	@CrossOrigin
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public void cadastrar(@RequestBody AnexosOshDTO anexosOshDTO,
			@RequestHeader("Authorization") String token) {
		anexosOshService.salvar(anexosOshDTO, token);

	}
}
