package moduloAnexos.service.dto;

import java.util.Date;

import moduloAnexos.util.ConvertObjectToJsonCesan;
import moduloAnexos.util.customAnnotation.JsonCesanNotSerializable;

public class AnexosClienteDTO {

	private Long id;
	@JsonCesanNotSerializable
	private Date dataHoraInclusao;
	private Integer cdCliente;
	private String descricao;
	private String usuario;
	private String nomeArquivo;

	public AnexosClienteDTO(Long id, Date dataHoraInclusao, Integer cdCliente, String descricao, String usuario,String nomeArquivo) {
		this.id = id;
		this.dataHoraInclusao = dataHoraInclusao;
		this.cdCliente = cdCliente;
		this.descricao = descricao;
		this.usuario = usuario;
		this.nomeArquivo=nomeArquivo;
	}

	public AnexosClienteDTO() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataHoraInclusao() {
		return dataHoraInclusao;
	}

	public void setDataHoraInclusao(Date dataHoraInclusao) {
		this.dataHoraInclusao = dataHoraInclusao;
	}

	public Integer getCdCliente() {
		return cdCliente;
	}

	public void setCdCliente(Integer cdCliente) {
		this.cdCliente = cdCliente;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public String toJson() {

		return ConvertObjectToJsonCesan.execute(this);
	}

}
