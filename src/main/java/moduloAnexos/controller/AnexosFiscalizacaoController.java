package moduloAnexos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import moduloAnexos.service.AnexosFiscalizacaoService;
import moduloAnexos.service.dto.AnexosFiscalizacaoDTO;
import moduloAnexos.service.dto.AnexosFiscalizacaoWrapperDTO;
import moduloAnexos.service.dto.DownloadArquivoDTO;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping("/backend-anexos/contratoFiscalizacao")
public class AnexosFiscalizacaoController {
	@Autowired
	private AnexosFiscalizacaoService anexosFiscalizacaoService;

	@CrossOrigin
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping                                            
	public AnexosFiscalizacaoDTO cadastrar(@RequestBody AnexosFiscalizacaoDTO anexosMedicaoSsDTO,@RequestHeader("Authorization") String token) {
		return anexosFiscalizacaoService.salvar(anexosMedicaoSsDTO, token);
	}
	
	@CrossOrigin
	@GetMapping("/listaAnexo")
	public AnexosFiscalizacaoWrapperDTO buscarPorIdSS(@RequestParam(value = "idFiscalizacoesSS", required = true) Integer idFiscalizacoesSS ,Pageable pageable,@RequestHeader("Authorization") String token) {
		return anexosFiscalizacaoService.buscarPorIdSs(idFiscalizacoesSS, pageable);
	}
	
	@CrossOrigin
	@GetMapping("/downloadArquivo")
	public DownloadArquivoDTO downloadArquivo(@RequestParam(value = "id", required = true) Long id) {
		return anexosFiscalizacaoService.buscarArquivoPorId(id);
	}
	
	@CrossOrigin
	@DeleteMapping
	public void excluirArquivo(@RequestParam(value = "id", required = true) Long id,@RequestHeader("Authorization") String token) {
		 anexosFiscalizacaoService.excluir(id, token);
	}
	
	@CrossOrigin
	@DeleteMapping("/excluirTodosPorFiscalizacao")                 
	public void excluirTodosPorFiscalizacao(@RequestParam(value = "idFiscalizacoesSS", required = true) Integer idFiscalizacoesSS,@RequestHeader("Authorization") String token) {
		 anexosFiscalizacaoService.excluirTodosPorFiscalizacao(idFiscalizacoesSS);
	}

}
