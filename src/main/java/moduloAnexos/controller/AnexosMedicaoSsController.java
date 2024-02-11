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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import moduloAnexos.service.AnexosMedicaoSsService;
import moduloAnexos.service.dto.AnexosMedicaoSsDTO;
import moduloAnexos.service.dto.AnexosMedicaoSsWrapperDTO;
import moduloAnexos.service.dto.DownloadArquivoDTO;

@Api(tags="Anexos medição da SS", description="Endpoints para anexos de medição da SS")
@RestController
@RequestMapping("/backend-anexosMedicaoSs/anexos")
public class AnexosMedicaoSsController {
	
	@Autowired
	private AnexosMedicaoSsService anexosMedicaoSsService;

	@ApiOperation(value="POST para salvar os anexos - deve-se passar um AnexosMedicaoSsDTO conforme modelo abaixo, "
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
	public void cadastrar(@RequestBody AnexosMedicaoSsDTO anexosMedicaoSsDTO,
			@RequestHeader("Authorization") String token) {
		 anexosMedicaoSsService.salvar(anexosMedicaoSsDTO, token);

	}
	
	@ApiOperation(value="PUT para atualizar os anexos - deve-se passar um AnexosMedicaoSsDTO conforme modelo abaixo, "
			+ "além do token de autorização nos Headers")
    @ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK: Anexo atualizado com sucesso"),
			@ApiResponse(code = 201, message = "Created: Anexo atualizado com sucesso"),
			@ApiResponse(code = 400, message = "BAD REQUEST: Erro na requisição - verificar corpo da requisição"),
            @ApiResponse(code = 401, message = "Unauthorized: Não autorizado")
    }
    )
	@CrossOrigin
	@PutMapping                                            
	public void atualizar(@RequestBody AnexosMedicaoSsDTO anexosMedicaoSsDTO,
			@RequestHeader("Authorization") String token) {
		 anexosMedicaoSsService.atualizar(anexosMedicaoSsDTO, token);

	}
	
	@ApiOperation(value="GET para buscar anexos pelo id - deve-se passar o id e se é anexoAntigo como parâmetro")
    @ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK: Requisição bem sucedida"),
            @ApiResponse(code = 401, message = "Unauthorized: Não autorizado")
    }
    )
	@CrossOrigin
	@GetMapping
	public AnexosMedicaoSsDTO buscarPorId(@RequestParam(value = "id", required = true) Long id,
			@RequestParam(value = "anexoAntigo", required = true) Boolean anexoAntigo) {
		return anexosMedicaoSsService.buscarPorId(id, anexoAntigo);

	}
	
	@ApiOperation(value="GET para retornar uma lista de anexos pelo código da SS - deve-se passar o código da SS como parâmetro, "
			+ "além do token de autorização nos headers")
    @ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK: Requisição bem sucedida"),
            @ApiResponse(code = 401, message = "Unauthorized: Não autorizado")
    }
    )
	@CrossOrigin
	@GetMapping("/listAnexo")
	public AnexosMedicaoSsWrapperDTO buscarPorIdSS(
			@RequestParam(value = "refAtendimento", required = true) String refAtendimento,
			@RequestParam(value = "codAtendimento", required = true) Integer codAtendimento,@RequestParam(value = "ssSequencial", required = true) Short ssSequencial ,Pageable pageable,@RequestHeader("Authorization") String token) {
		return anexosMedicaoSsService.buscarPorIdSs(codAtendimento, refAtendimento, ssSequencial, pageable);
	}
	
	@ApiOperation(value="GET para retornar os dados e o nome do arquivo para downloads - deve-se passar o id e se é anexoAntigo como parâmetro")
    @ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK: Requisição bem sucedida"),
            @ApiResponse(code = 401, message = "Unauthorized: Não autorizado")
    }
    )
	@CrossOrigin
	@GetMapping("/downloadArquivo")
	public DownloadArquivoDTO downloadArquivo(@RequestParam(value = "id", required = true) Long id,
			@RequestParam(value = "anexoAntigo", required = true) Boolean anexoAntigo) {
		return anexosMedicaoSsService.buscarArquivoPorId(id, anexoAntigo);

	}
	
	@ApiOperation(value="DELETE para exclusão de um anexo - deve-se passar o id do anexo como parâmetro, além do token de autorização nos headers")
    @ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK: Anexo excluído com sucesso"),
            @ApiResponse(code = 401, message = "Unauthorized: Não autorizado")
    }
    )
	@CrossOrigin
	@DeleteMapping
	public void excluirArquivo(@RequestParam(value = "id", required = true) Long id,
			@RequestParam(value = "anexoAntigo", required = true) Boolean anexoAntigo,@RequestHeader("Authorization") String token) {
		 anexosMedicaoSsService.excluir(id, anexoAntigo, token);

	}
}
