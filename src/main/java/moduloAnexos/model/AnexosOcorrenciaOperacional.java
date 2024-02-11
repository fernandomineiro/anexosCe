package moduloAnexos.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import moduloAnexos.util.DateFormatDefault;
import moduloAnexos.util.customAnnotation.DateTimeFormatCesan;
import moduloAnexos.util.customAnnotation.JsonCesanNotSerializable;

@Entity
@Table(name = "SRV_ANEXOS_OCORRENCIA_OPERACIONAL")
public class AnexosOcorrenciaOperacional {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonCesanNotSerializable
	private Long id;

	@Column(name = "DATA_HORA_INCLUSAO", columnDefinition = "DATETIME", updatable = false, insertable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormatCesan(formatString = DateFormatDefault.FORMATTER_DATE_TIME)
	@JsonCesanNotSerializable
	private Date dataHoraInclusao;

	private Long idOcorrenciaOperacional;

	@Column(name = "DESCRICAO")
	private String descricao;

	@Column(name = "USUARIO")
	private String usuario;

	@Column(name = "NOME_AQUIVO")
	private String nomeArquivo;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "ARQUIVO")
	private byte[] arquivo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataHoraInclusao() {
		return dataHoraInclusao;
	}

	public void setDataHoraInclusao(Date Date) {
		this.dataHoraInclusao = Date;
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

	public byte[] getArquivo() {
		return arquivo;
	}

	public void setArquivo(byte[] arquivo) {
		this.arquivo = arquivo;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

}
