package moduloAnexos.service.dto;

import java.util.List;

public class AnexosClienteWrapperDTO {

	private List<AnexosClienteDTO> listAnexosClienteDTO;

	private Long totalRegistros;
	
	private Integer clienteExcluido = 0;
	private Integer clienteValidado  = 0;
	private Integer clienteEspecial  = 0;
	private Integer clientePublico  = 0;
	private String nomeCliente;

	public List<AnexosClienteDTO> getListAnexosClienteDTO() {
		return listAnexosClienteDTO;
	}

	public void setListAnexosClienteDTO(List<AnexosClienteDTO> listAnexosClienteDTO) {
		this.listAnexosClienteDTO = listAnexosClienteDTO;
	}

	public Long getTotalRegistros() {
		return totalRegistros;
	}

	public void setTotalRegistros(Long totalRegistros) {
		this.totalRegistros = totalRegistros;
	}

	public Integer getClienteExcluido() {
		return clienteExcluido;
	}

	public void setClienteExcluido(Integer clienteExcluido) {
		this.clienteExcluido = clienteExcluido;
	}

	public Integer getClienteValidado() {
		return clienteValidado;
	}

	public void setClienteValidado(Integer clienteValidado) {
		this.clienteValidado = clienteValidado;
	}

	public Integer getClienteEspecial() {
		return clienteEspecial;
	}

	public void setClienteEspecial(Integer clienteEspecial) {
		this.clienteEspecial = clienteEspecial;
	}

	public Integer getClientePublico() {
		return clientePublico;
	}

	public void setClientePublico(Integer clientePublico) {
		this.clientePublico = clientePublico;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

}
