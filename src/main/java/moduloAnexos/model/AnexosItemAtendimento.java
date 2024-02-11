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
@Table(name = "SRV_ANEXOS_ITEM_ATENDIMENTO")
public class AnexosItemAtendimento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonCesanNotSerializable
	private Long id;
	@Column(name = "DATA_HORA_INCLUSAO", columnDefinition = "DATETIME", updatable = false, insertable = false)
	@JsonCesanNotSerializable
	private LocalDateTime dataHoraInclusao;
	@Column(name = "CD_ATENDIMENTO")
	private Integer codAtendimento;
	@Column(name = "REF_ATENDIMENTO")
	private Integer refAtendimento;
	@Column(name = "SEQ_SS")
	private Short seqSS;
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

	public Integer getCodAtendimento() {
		return codAtendimento;
	}

	public void setCodAtendimento(Integer codAtendimento) {
		this.codAtendimento = codAtendimento;
	}

	public Integer getRefAtendimento() {
		return refAtendimento;
	}

	public void setRefAtendimento(Integer refAtendimento) {
		this.refAtendimento = refAtendimento;
	}

	public Short getSeqSS() {
		return seqSS;
	}

	public void setSeqSS(Short seqSS) {
		this.seqSS = seqSS;
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
