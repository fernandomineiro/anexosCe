package moduloAnexos.model;

import java.time.LocalDateTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import moduloAnexos.util.customAnnotation.JsonCesanNotSerializable;

@Entity
@Table(name = "SRV_ANEXOS_IRREGULARIDADE")
public class AnexosRetornoIrregularidade {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonCesanNotSerializable
	private Long id;
	@Column(name = "DATA_HORA_INCLUSAO",columnDefinition = "DATETIME", updatable = false, insertable = false)
	@JsonCesanNotSerializable
	private LocalDateTime dataHoraInclusao;
	@Column(name = "MATRICULA")
	private Integer matricula;
	@Column(name = "DV")
	private Short dv;
	@Column(name = "REFERENCIA")
	private Integer referencia;
	@Column(name = "NUMERO_PESQUISA")
	private Integer numPesquisa;
	@Column(name = "USUARIO")
	private String usuario;
	@Column(name = "NOME_ARQUIVO")
	private String nomeArquivo;
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "ARQUIVO")
	private byte[] arquivo;
	@Column(name = "TAMANHO_ARQUIVO")
	private Integer tamanhoArquivo;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public LocalDateTime getDataHoraInclusao() {
		return dataHoraInclusao;
	}
	
	public void setDataHoraInclusao(LocalDateTime dataHoraInclusao) {
		this.dataHoraInclusao = dataHoraInclusao;
	}
	
	public Integer getMatricula() {
		return matricula;
	}
	
	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}
	
	public Short getDv() {
		return dv;
	}

	public void setDv(Short dv) {
		this.dv = dv;
	}

	public Integer getReferencia() {
		return referencia;
	}
	
	public void setReferencia(Integer referencia) {
		this.referencia = referencia;
	}
	
	public Integer getNumPesquisa() {
		return numPesquisa;
	}
	
	public void setNumPesquisa(Integer numPesquisa) {
		this.numPesquisa = numPesquisa;
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
	
	public byte[] getArquivo() {
		return arquivo;
	}
	
	public void setArquivo(byte[] arquivo) {
		this.arquivo = arquivo;
	}
	
	public Integer getTamanhoArquivo() {
		return tamanhoArquivo;
	}
	
	public void setTamanhoArquivo(Integer tamanhoArquivo) {
		this.tamanhoArquivo = tamanhoArquivo;
	}
}
