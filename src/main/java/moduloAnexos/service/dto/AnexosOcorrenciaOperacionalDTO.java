package moduloAnexos.service.dto;

import java.util.Date;

import moduloAnexos.util.ConvertObjectToJsonCesan;
import moduloAnexos.util.customAnnotation.JsonCesanNotSerializable;

public class AnexosOcorrenciaOperacionalDTO {

	private Long id;
	@JsonCesanNotSerializable
	private Date dataHoraInclusao;
	private Long idOcorrenciaOperacional;
	private String descricao;
	private String usuario;
	private String nomeArquivo;

	public AnexosOcorrenciaOperacionalDTO(Long id, Date dataHoraInclusao, Long idOcorrenciaOperacional, 
			String descricao, String usuario, String nomeArquivo) {
		super();
		this.id = id;
		this.dataHoraInclusao = dataHoraInclusao;
		this.idOcorrenciaOperacional = idOcorrenciaOperacional;
		this.descricao = descricao;
		this.usuario = usuario;
		this.nomeArquivo = nomeArquivo;
	}

	public AnexosOcorrenciaOperacionalDTO() {

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

	public Long getIdOcorrenciaOperacional() {
		return idOcorrenciaOperacional;
	}

	public void setIdOcorrenciaOperacional(Long idOcorrenciaOperacional) {
		this.idOcorrenciaOperacional = idOcorrenciaOperacional;
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
