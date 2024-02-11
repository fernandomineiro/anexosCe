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

import moduloAnexos.service.AnexosItemAtendimentoService;
import moduloAnexos.service.dto.AnexosItemAtendimentoDTO;
import moduloAnexos.service.dto.AnexosItemAtendimentoWrapperDTO;
import moduloAnexos.service.dto.DownloadArquivoDTO;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping("/backend-anexosItemAtendimento/anexos")
public class AnexosItemAtendimentoController {
	@Autowired
	private AnexosItemAtendimentoService anexosItemAtendimentoService;

	@CrossOrigin
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping

	public AnexosItemAtendimentoDTO cadastrar(@RequestBody AnexosItemAtendimentoDTO anexosItemAtendimentoDTO,
			@RequestHeader("Authorization") String token) {
		return anexosItemAtendimentoService.salvar(anexosItemAtendimentoDTO, token);

	}

	@CrossOrigin
	@DeleteMapping("/{id}")
	public void excluir(@PathVariable(value = "id") Long id, @RequestHeader("Authorization") String token) {
		anexosItemAtendimentoService.excluir(id, token);
	}

	@CrossOrigin
	@GetMapping
	public AnexosItemAtendimentoWrapperDTO buscarPorID(
			@RequestParam(value = "refAtendimento", required = true) String refAtendimento,
			@RequestParam(value = "codAtendimento", required = true) Integer codAtendimento,
			@RequestParam(value = "seqAtendimento", required = true) Short seqAtendimento, Pageable pageable) {
		return anexosItemAtendimentoService.buscarPorId(codAtendimento, refAtendimento, seqAtendimento, pageable);
	}

	@CrossOrigin
	@GetMapping("/downloadArquivo/{id}")
	public DownloadArquivoDTO downloadArquivo(@PathVariable Long id, HttpServletResponse response) {
		return anexosItemAtendimentoService.buscarArquivoPorId(id);

	}
}
