package moduloAnexos.excecoes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.json.JSONException;
import org.json.JSONObject;
import org.mapstruct.ap.shaded.freemarker.template.utility.NullArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import moduloAnexos.service.dto.ExcessoesRetornoApiDTO;
import moduloAnexos.util.FormatarMensagemExceptionValidation;
import moduloAnexos.util.LogErro;



@ControllerAdvice
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	LogErro logErro;

	
	@ExceptionHandler(DadoInvalido.class)
	public ResponseEntity<String> handleRetornoDadoInvalido(DadoInvalido e,
			HttpServletRequest request, HandlerMethod handlerMethod) {	
		return new ResponseEntity<String>(JSONObject.quote(e.getLocalizedMessage()), ApiStatus.DADO_INVALIDO.httpStatus);
	}
	
	@ExceptionHandler(Vazio.class)
	public ResponseEntity<String> handleRetornoVazio(Vazio e,
			HttpServletRequest request, HandlerMethod handlerMethod) {	
		return new ResponseEntity<String>(JSONObject.quote(ApiStatus.SEM_RESULTADO.message), ApiStatus.SEM_RESULTADO.httpStatus);
	}
	
	@ExceptionHandler(ExcecaoRegraNegocio.class)
	public ResponseEntity<ExcessoesRetornoApiDTO> handleExcecaoRegraNegocio(ExcecaoRegraNegocio e,
			HttpServletRequest request, HandlerMethod handlerMethod) {	
		ExcessoesRetornoApiDTO excessoesMensagemRetorno = new ExcessoesRetornoApiDTO(409,  ApiStatus.ERRO_REQUISICAO.httpStatus.toString(), e.getMessage());
		return new  ResponseEntity<ExcessoesRetornoApiDTO>(excessoesMensagemRetorno, ApiStatus.ERRO_REQUISICAO.httpStatus);
	}
	
	/**
	 * @author Ivan Alves
	 * <b>Toda vez que a aplicação gerar a excessão MsgGenericaPersonalizadaException a função é chamada</b>
	 * A classe instancia a classe que constroi a mensagem para o front end
	 */
	@ExceptionHandler(MsgGenericaPersonalizadaException.class)	
	public ResponseEntity<ExcessoesRetornoApiDTO> handleMsgGenericaPersonalizada(MsgGenericaPersonalizadaException e, HttpServletRequest request, HandlerMethod handlerMethod) {

		ExcessoesRetornoApiDTO excessoesMensagemRetorno = 
				new ExcessoesRetornoApiDTO(400, ApiStatus.DADO_INVALIDO.httpStatus.toString(), e.getMessage());
		
		return new ResponseEntity<ExcessoesRetornoApiDTO>(excessoesMensagemRetorno, ApiStatus.DADO_INVALIDO.httpStatus);
	}
	
	/**
	 * @author Ivan Alves
	 * <b>Toda vez que a aplicação gerar a excessão NullArgumentException a função é chamada</b>
	 * A classe instancia a classe que constroi a mensagem para o front end
	 */
	@ExceptionHandler(NullArgumentException.class)	
	public ResponseEntity<ExcessoesRetornoApiDTO> handleDadoObrigatorioNaoInformado(NullArgumentException e, HttpServletRequest request, HandlerMethod handlerMethod) {
		ExcessoesRetornoApiDTO excessoesMensagemRetorno = 
				new ExcessoesRetornoApiDTO(400, ApiStatus.DADO_INVALIDO.httpStatus.toString(), e.getMessage());
		logErro.logErroGeral(e, "NullArgumentException.class");
		return new ResponseEntity<ExcessoesRetornoApiDTO>(excessoesMensagemRetorno, ApiStatus.DADO_INVALIDO.httpStatus);
	}
	
	/**
	 * @author Ivan Alves
	 * <b>Execessão gerada quando os dados de entidade recebida esta fora do escopo informado</b>
	 * EX: @Size, @NotNull especificado na entidade
	 * O código abaixo chama a classe responsável por  
	 * captura a mensagem. Formata para remover informações complementares, deixando somente o texto configurado
	 * no arquivo 'ValidationMenssage.properties'
	 * Depois o metodo chama a DTO responsável por construir um objeto de mensagens legível ao cliente
	 * retorna o objeto ao cliente com o codigo de status adequado
	 *
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ExcessoesRetornoApiDTO> handleRetornaDadoInvalidoSpringValidation(ConstraintViolationException e,
			HttpServletRequest request, HandlerMethod handlerMethod) throws JSONException {	

		FormatarMensagemExceptionValidation formatarMensagem = new FormatarMensagemExceptionValidation();
		String mensagemExcessaoFormatada = formatarMensagem.executarProcedimentoObterMensagemPersonalizadaUsuario(e);

		ExcessoesRetornoApiDTO excessoesMensagemRetorno = 
						new ExcessoesRetornoApiDTO(400, ApiStatus.DADO_INVALIDO.httpStatus.toString(), mensagemExcessaoFormatada);
		return new ResponseEntity<ExcessoesRetornoApiDTO>(excessoesMensagemRetorno, ApiStatus.DADO_INVALIDO.httpStatus);
	}
	

	@ExceptionHandler(Exception.class)	
	public ResponseEntity<ExcessoesRetornoApiDTO> handleGenericException(Exception e, HttpServletRequest request, HandlerMethod handlerMethod) {
		ExcessoesRetornoApiDTO excessoesMensagemRetorno = 
				new ExcessoesRetornoApiDTO(400, ApiStatus.DADO_INVALIDO.httpStatus.toString(), e.getMessage());
		logErro.logErroGeral(e, "Exception.class");
		return new ResponseEntity<ExcessoesRetornoApiDTO>(excessoesMensagemRetorno, ApiStatus.DADO_INVALIDO.httpStatus);
	}
}