package moduloAnexos.excecoes;


import moduloAnexos.service.dto.ExcessoesRetornoApiDTO;
import moduloAnexos.service.dto.agencia.ExcecaoAgenciaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class HandlerBeenValidation {
	final public static String GATILHO_EXCESSAO_SIZE = "gatilhoExcessaoParaSize";
	final public static String GATILHO_EXCESSAO_DADO_INVALIDO = "gatilhoExcessaoDadoInvalido";
	@Autowired
	private MessageSource messageSource;
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)	
	public ResponseEntity<Object> handleBeenValidation(MethodArgumentNotValidException e, HttpServletRequest request, HandlerMethod handlerMethod) {
		String mensagem = messageSource.getMessage(e.getBindingResult().getFieldError(), LocaleContextHolder.getLocale());
		if(request.getRequestURL().toString().contains("agenciaVirtual")){
			MsgGenericaParametrizadaException msg = new MsgGenericaParametrizadaException(MsgGenericaParametrizadaEnum.MENSAGEM_ERRO_VALIDACAO_SS).setStatusCode(400);
			if(mensagem.equals(GATILHO_EXCESSAO_SIZE)) {
				//msg = new MsgGenericaParametrizadaException(MsgGenericaParametrizadaEnum.TAMANHO_CAMPO_INVALIDO).setStatusCode(422);
			}else if(mensagem.equals(GATILHO_EXCESSAO_DADO_INVALIDO)){
				//msg = new MsgGenericaParametrizadaException(MsgGenericaParametrizadaEnum.DADO_COM_INFORMACAO_INVALIDA).setStatusCode(422);
			}else{
				//msg = new MsgGenericaParametrizadaException(MsgGenericaParametrizadaEnum.DADO_OBRIGATORIO_NAO_INFORMADO).setStatusCode(400);
			}
			ExcecaoAgenciaDTO excessoesMensagemRetorno = new ExcecaoAgenciaDTO(msg.getClientCode(), msg.getMessage(), msg.getStatusCode(), request.getRequestURI());
			return new ResponseEntity<Object>(excessoesMensagemRetorno, HttpStatus.valueOf(msg.getStatusCode()));
		}else{
			ExcessoesRetornoApiDTO excessoesMensagemRetorno =
					new ExcessoesRetornoApiDTO(400, ApiStatus.DADO_INVALIDO.httpStatus.toString(), mensagem);
			return new ResponseEntity<Object>(excessoesMensagemRetorno, ApiStatus.DADO_INVALIDO.httpStatus);
		}
	}

}
