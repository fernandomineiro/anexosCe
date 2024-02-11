package moduloAnexos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import moduloAnexos.service.AnexosIrregularidadeService;

@Api(tags = "Anexos Retorno Irregularidade", description = "Endpoint para anexar arquivos de Retorno de Irregularidade")
@RestController
@RequestMapping("/backend-anexosRetornoIrregularidade/anexos")
public class AnexosRetornoIrregularidadeController {

	@Autowired
	private AnexosIrregularidadeService anexosIrregularidadeService;
	
	@ApiOperation(value = "POST para anexar arquivos referentes aos registros do Arquivo de Retorno - deve-se passar uma lista de arquivos, além do token de acesso nos Headers")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK: Arquivo(s) enviado(s) com sucesso"),
			@ApiResponse(code = 400, message = "BAD REQUEST: Erro na requisição - verificar corpo da requisição"),
            @ApiResponse(code = 401, message = "Unauthorized: Não autorizado")
    })
	@CrossOrigin
	@PostMapping(consumes =  {"multipart/form-data"})
	public void cadastrar(@RequestPart("anexosIrregularidade") List<MultipartFile> anexosIrregularidade,
			@RequestHeader("Authorization") String token) {
		anexosIrregularidadeService.salvar(anexosIrregularidade, token);
	}
}
