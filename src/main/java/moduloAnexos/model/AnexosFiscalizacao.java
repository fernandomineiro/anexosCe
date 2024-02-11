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
@Table(name = "SRV_ANEXOS_FISCALIZACAO_CONTRATO")
public class AnexosFiscalizacao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonCesanNotSerializable
	private Long id;
	
	@Column(name = "ID_FISCALIZACOES_SS")
	private Integer idFiscalizacoesSS;
	
	@Column(name = "DATA_HORA_INCLUSAO")
	private LocalDateTime dataHoraInclusao;
	
	@Column(name = "CD_ATENDIMENTO")
	private Integer cdAtendimento;
	
	@Column(name = "REF_ATENDIMENTO")
	private Integer refAtendimento;
	
	@Column(name = "SEQ_SS")
	private Short seqSS;
	
	@Column(name = "DESCRICAO" , length = 150)
	private String descricao;
	
	@Column(name = "USUARIO", length = 60)
	private String usuario;
	
	@Column(name = "NOME_AQUIVO" , length=60)
	private String nomeArquivo;
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "ARQUIVO")
	private byte[] arquivo;
	
	@Column(name = "TAMANHO_ARQUIVO")
	private Integer tamanhoArquivo;
	
	@Column(name = "ACESSO_RESTRITO" ,length = 1)
	private String acessoRestrito;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getIdFiscalizacoesSS() {
		return idFiscalizacoesSS;
	}

	public void setIdFiscalizacoesSS(Integer idFiscalizacoesSS) {
		this.idFiscalizacoesSS = idFiscalizacoesSS;
	}

	public LocalDateTime getDataHoraInclusao() {
		return dataHoraInclusao;
	}

	public void setDataHoraInclusao(LocalDateTime dataHoraInclusao) {
		this.dataHoraInclusao = dataHoraInclusao;
	}

	public Integer getCdAtendimento() {
		return cdAtendimento;
	}

	public void setCdAtendimento(Integer cdAtendimento) {
		this.cdAtendimento = cdAtendimento;
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

	public String getAcessoRestrito() {
		return acessoRestrito;
	}

	public void setAcessoRestrito(String acessoRestrito) {
		this.acessoRestrito = acessoRestrito;
	}
	
}
