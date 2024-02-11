package moduloAnexos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import moduloAnexos.service.AnexosDadosSsService;
import moduloAnexos.service.AnexosMedicaoSsService;
import moduloAnexos.service.dto.AnexosDadosSsDTO;
import moduloAnexos.service.dto.AnexosSsGenericoWrapperDTO;
import moduloAnexos.service.dto.DownloadArquivoDTO;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping("/backend-anexosSolicitacaoServicoDados/anexos")
public class AnexosSolicitacaoServicoDadosController {

	@Autowired
	private AnexosMedicaoSsService anexosMedicaoSsService;
	@Autowired
	private AnexosDadosSsService anexosDadosSsService;
	
	@CrossOrigin
	@DeleteMapping("/medicao")
	public void excluirArquivo(@RequestParam(value = "idMedicao", required = false) Long idMedicao,
			@RequestParam(value = "refAtendimento") Integer refAtendimento, 
			@RequestParam(value = "cdAtendimento") Integer cdAtendimento,
			@RequestParam(value = "seqSS") Short seqSS,
			@RequestParam(value = "idSs") Integer idSs,
			@RequestHeader("Authorization") String token) {
		anexosMedicaoSsService.excluirTodosPorSs(idMedicao, refAtendimento, cdAtendimento, seqSS, idSs, token);

	}
	
	@CrossOrigin
	@GetMapping("/listAnexo")
	public AnexosSsGenericoWrapperDTO buscarPorIdSS(
			@RequestParam(value = "refAtendimento", required = true) String refAtendimento,
			@RequestParam(value = "codAtendimento", required = true) Integer codAtendimento,@RequestParam(value = "ssSequencial", required = true) Short ssSequencial ,Pageable pageable,@RequestHeader("Authorization") String token) {
		return anexosDadosSsService.buscarIdSs(codAtendimento, refAtendimento, ssSequencial,token, pageable);
	}
	
	@CrossOrigin
	@GetMapping("/listAnexoAll")
	public AnexosSsGenericoWrapperDTO buscarPorIdSS2(
			@RequestParam(value = "refAtendimento", required = true) String refAtendimento,
			@RequestParam(value = "codAtendimento", required = true) Integer codAtendimento,@RequestParam(value = "ssSequencial", required = true) Short ssSequencial ,Pageable pageable,@RequestHeader("Authorization") String token) {
		return anexosDadosSsService.buscarIdSs2(codAtendimento, refAtendimento, ssSequencial,token, pageable);
	}
	
	@CrossOrigin
	@GetMapping("/downloadArquivo")
	public DownloadArquivoDTO downloadArquivo(@RequestParam(value = "id", required = true) Long id,
			@RequestParam(value = "origemArquivo", required = true) String origemArquivo) {
		return anexosDadosSsService.buscarArquivoPorId(id, origemArquivo);

	}
	

	@CrossOrigin
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping                                            
	public void cadastrar(@RequestBody AnexosDadosSsDTO anexosDadosSsDTO,
			@RequestHeader("Authorization") String token) {
		anexosDadosSsService.salvar(anexosDadosSsDTO, token);

	}
	
	@CrossOrigin
	@PutMapping                                            
	public void atualizar(@RequestBody AnexosDadosSsDTO anexosDadosSsDTO,
			@RequestHeader("Authorization") String token) {
		anexosDadosSsService.atualizar(anexosDadosSsDTO, token);

	}
	

	@CrossOrigin
	@DeleteMapping
	public void excluirArquivo(@RequestParam(value = "id", required = true) Long id,
			@RequestHeader("Authorization") String token) {
		anexosDadosSsService.excluir(id, token);

	}
	
}
