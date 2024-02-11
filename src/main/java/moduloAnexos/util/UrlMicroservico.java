package moduloAnexos.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UrlMicroservico {

	@Value("${microservico.cliente}")
	private String urlCliente;
	@Value("${microservico.servico}")
	private String urlServico;
	@Value("${microservico.imovel}")
	private String urlImovel;

	
	public String getUrlCliente() {
		return urlCliente;
	}
	public void setUrlCliente(String urlCliente) {
		this.urlCliente = urlCliente;
	}
	public String getUrlServico() {
		return urlServico;
	}
	public void setUrlServico(String urlServico) {
		this.urlServico = urlServico;
	}
	public String getUrlImovel() {
		return urlImovel;
	}
	public void setUrlImovel(String urlImovel) {
		this.urlImovel = urlImovel;
	}

	
}
