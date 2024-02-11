package moduloAnexos.service.dto;

import java.time.LocalDateTime;

public class AnexosSsGenericoDTO {

	private Long id;
	private LocalDateTime dataHoraInclusao;
	private Integer codAtendimento;
	private String refAtendimento;
	private Short seqSS;
	private String descricao;
	private String usuario;
	private String nomeArquivo;
	private String acessoRestrito;
	private String origemArquivo;
	private Integer tamanhoArquivo;
	
	public Integer getTamanhoArquivo() {
		return tamanhoArquivo;
	}

	public void setTamanhoArquivo(Integer tamanhoArquivo) {
		this.tamanhoArquivo = tamanhoArquivo;
	}


	
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
	public String getRefAtendimento() {
		return refAtendimento;
	}
	public void setRefAtendimento(String refAtendimento) {
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
	public String getOrigemArquivo() {
		return origemArquivo;
	}
	public void setOrigemArquivo(String origemArquivo) {
		this.origemArquivo = origemArquivo;
	}
	public String getAcessoRestrito() {
		return acessoRestrito;
	}
	public void setAcessoRestrito(String acessoRestrito) {
		this.acessoRestrito = acessoRestrito;
	}
	
	
	
}
