package moduloAnexos.service.dto;

import java.util.Date;

import moduloAnexos.util.ConvertObjectToJsonCesan;
import moduloAnexos.util.customAnnotation.JsonCesanNotSerializable;

public class AnexosImovelDTO {

	private Long id;
	@JsonCesanNotSerializable
	private Date dataHoraInclusao;
	private Integer matriculaImovel;
	private String descricao;
	private String usuario;
	private String nomeArquivo;

	public AnexosImovelDTO(Long id, Date dataHoraInclusao, Integer matriculaImovel, String descricao, String usuario,String nomeArquivo) {
		this.id = id;
		this.dataHoraInclusao = dataHoraInclusao;
		this.matriculaImovel = matriculaImovel;
		this.descricao = descricao;
		this.usuario = usuario;
		this.nomeArquivo=nomeArquivo;
	}

	public AnexosImovelDTO() {

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

	public Integer getMatriculaImovel() {
		return matriculaImovel;
	}

	public void setMatriculaImovel(Integer matriculaImovel) {
		this.matriculaImovel = matriculaImovel;
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
