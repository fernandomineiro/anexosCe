package moduloAnexos.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import moduloAnexos.service.AnexosBaixarSolicitacaoServicoService;
import moduloAnexos.service.dto.AnexosBaixarSolicitacaoServicoDTO;
import moduloAnexos.service.dto.AnexosBaixarSolicitacaoServicoWrapperDTO;
import moduloAnexos.service.dto.DownloadArquivoDTO;

@Api(tags="Anexos baixa da SS", description="Endpoints para anexos de baixa da SS")
@RestController
@RequestMapping("/backend-anexosBaixarSolicitacaoServico/anexos")
public class AnexosBaixarSolicitacaoServicoController {
	
	@Autowired
	private AnexosBaixarSolicitacaoServicoService anexosBaixarSSService;

	@ApiOperation(value="GET para buscar anexos pelo código da SS - deve-se passar o código da SS como parâmetro")
    @ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK: Requisição bem sucedida"),
            @ApiResponse(code = 401, message = "Unauthorized: Não autorizado")
    }
    )
	@CrossOrigin
	@GetMapping
	public AnexosBaixarSolicitacaoServicoWrapperDTO buscarPorID(
			@RequestParam(value = "refAtendimento", required = true) String refAtendimento,
			@RequestParam(value = "codAtendimento", required = true) Integer codAtendimento,@RequestParam(value = "ssSequencial", required = true) Short ssSequencial ,Pageable pageable) {
		return this.anexosBaixarSSService.buscarPorId(codAtendimento, refAtendimento, ssSequencial, pageable);
	}
	
	@ApiOperation(value="DELETE para exclusão de um anexo - deve-se passar o id do anexo como parâmetro, além do token de autorização nos headers")
    @ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK: Anexo excluído com sucesso"),
            @ApiResponse(code = 401, message = "Unauthorized: Não autorizado")
    }
    )
	@CrossOrigin
	@DeleteMapping("/{id}")
	public void excluir(@PathVariable(value = "id") Long id, @RequestHeader("Authorization") String token) {
		this.anexosBaixarSSService.excluir(id, token);
	}
	
	@ApiOperation(value="POST para salvar os anexos - deve-se passar um AnexosBaixarSolicitacaoServicoDTO conforme modelo abaixo, "
			+ "além do token de autorização nos Headers")
    @ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK: Anexo cadastrado com sucesso"),
			@ApiResponse(code = 201, message = "Created: Anexo cadastrado com sucesso"),
			@ApiResponse(code = 400, message = "BAD REQUEST: Erro na requisição - verificar corpo da requisição"),
            @ApiResponse(code = 401, message = "Unauthorized: Não autorizado")
    }
    )
	@CrossOrigin
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping                                         
	public AnexosBaixarSolicitacaoServicoDTO cadastrar(@RequestBody @Valid AnexosBaixarSolicitacaoServicoDTO anexosBaixarSSDTO,
			@RequestHeader("Authorization") String token) {
		return this.anexosBaixarSSService.salvar(anexosBaixarSSDTO, token);
	}
	
	@ApiOperation(value="GET para retornar os dados e o nome do arquivo para downloads - deve-se passar o id como parâmetro")
    @ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK: Requisição bem sucedida"),
            @ApiResponse(code = 401, message = "Unauthorized: Não autorizado")
    }
    )
	@CrossOrigin
	@GetMapping("/downloadArquivo/{id}")
    public DownloadArquivoDTO downloadArquivo(@PathVariable Long id,HttpServletResponse response) {
		return this.anexosBaixarSSService.buscarArquivoPorId(id);
    }
	
	@ApiOperation(value="DELETE para exclusão de anexos pela SS - deve-se passar o código da SS como parâmetro, além do token de autorização nos headers")
    @ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK: Anexo excluído com sucesso"),
            @ApiResponse(code = 401, message = "Unauthorized: Não autorizado")
    }
    )
	@CrossOrigin
	@DeleteMapping("/dadosSs")
	public void excluirArquivo(
			@RequestParam(value = "refAtendimento") Integer refAtendimento, 
			@RequestParam(value = "cdAtendimento") Integer cdAtendimento,
			@RequestParam(value = "seqSS") Short seqSS,
			@RequestParam(value = "idSs") Integer idSs,
			@RequestHeader("Authorization") String token) {
		anexosBaixarSSService.excluirPorSs(cdAtendimento, refAtendimento, seqSS, idSs, token);

	}
}
