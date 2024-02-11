package moduloAnexos.autenticacao;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Component
public class JwtTokenAgenciaProvider {

	private static final ObjectMapper mapper = new ObjectMapper();

	public static Long buscarUserId(String token) {
		
		token = unpack(token);

		try {
			return mapper.readTree(token).findValue("user").findValue("id").asLong();
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	public static String buscarUserCpfCnpj(String token) {

		token = unpack(token);
		
		try {
			return mapper.readTree(token).findValue("user").findValue("cpfCnpj").asText();
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	public static String buscarUserEmail(String token) {

		token = unpack(token);
		
		try {
			return mapper.readTree(token).findValue("user").findValue("email").asText();
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	public static String buscarUserStatus(String token) {

		token = unpack(token);
		
		try {
			return mapper.readTree(token).findValue("user").findValue("status").asText();
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	public static Long buscarUserClienteId(String token) {

		token = unpack(token);
		
		try {
			return mapper.readTree(token).findValue("user").findValue("cliente").findValue("id").asLong();
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	public static String buscarUserClienteNome(String token) {

		token = unpack(token);
		
		try {
			return mapper.readTree(token).findValue("user").findValue("cliente").findValue("nome").asText();
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	public static List<Integer> buscarUserClienteMatriculas(String token) {
		
		token = unpack(token);		
		
		try {
			JsonNode jsonNode = mapper.readTree(token).findValue("user").findValue("matriculas");
			
			List<Integer> matriculas = new ArrayList<>();
			
			int index = 0;
			while (jsonNode.has(index)) {
				matriculas.add(jsonNode.get(index).asInt());
				index++;
			}
			return matriculas;
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
	

    private static String unpack(String token) {
    	
    	String parts[] = token.split("\\.");	
    	byte[] decodedBytes = Base64.getDecoder().decode(parts[1]);
    	return new String(decodedBytes);
    }

}

