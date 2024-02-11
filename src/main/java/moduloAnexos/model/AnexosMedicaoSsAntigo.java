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
@Table(name = "SRV_ARQUIVO")
public class AnexosMedicaoSsAntigo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonCesanNotSerializable
	@Column(name = "ID_ARQUIVO")
	private Long id;
	@Column(name = "ID_SOLICITACAO_SERVICO")
	private Long idSolicitacaoServico;
	@Column(name = "DT_ARQUIVO", updatable = false, insertable = false)
	@JsonCesanNotSerializable
	private LocalDateTime dataHoraInclusao;
	@Column(name = "DC_ARQUIVO" , length = 20)
	private String descricao;
	@Column(name = "ID_USUARIO", length = 15)
	private String usuario;
	@Column(name = "DC_NOME" , length=20)
	private String nomeArquivo;
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "BI_CONTEUDO")
	private byte[] arquivo;
	@Column(name = "ACESSO_RESTRITO" ,length = 1)
	private String acessoRestrito;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Long getIdSolicitacaoServico() {
		return idSolicitacaoServico;
	}
	public void setIdSolicitacaoServico(Long idSolicitacaoServico) {
		this.idSolicitacaoServico = idSolicitacaoServico;
	}
	public LocalDateTime getDataHoraInclusao() {
		return dataHoraInclusao;
	}
	public void setDataHoraInclusao(LocalDateTime dataHoraInclusao) {
		this.dataHoraInclusao = dataHoraInclusao;
	}
	public String getAcessoRestrito() {
		return acessoRestrito;
	}
	public void setAcessoRestrito(String acessoRestrito) {
		this.acessoRestrito = acessoRestrito;
	}
	
	
	
	
	
	
}
