package moduloAnexos.service.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import moduloAnexos.util.ConvertObjectToJsonCesan;
import moduloAnexos.util.customAnnotation.JsonCesanNotSerializable;

public class AnexosDadosSsDTO {

	private Long id;
	@JsonCesanNotSerializable
	private LocalDateTime dataHoraInclusao;
	private Integer codAtendimento;
	private String refAtendimento;
	private Short seqSS;
	private String descricao;
	private String usuario;
	private String nomeArquivo;
	private Integer tamanhoArquivo;
	private String origemArquivo;
	@JsonIgnore
	private byte[] arquivo;
	@JsonCesanNotSerializable
	private Integer totalArquivo;
	private String data;
	private String acessoRestrito;

	
	
	 public AnexosDadosSsDTO() {
			
		}
		public AnexosDadosSsDTO(Integer tamanhoArquivo) {
			this.tamanhoArquivo = tamanhoArquivo;
		}
		
		

		public AnexosDadosSsDTO(Long id, LocalDateTime dataHoraInclusao,Integer codAtendimento,Integer refAtendimento,Short seqSS, String descricao, String usuario, String nomeArquivo, Integer tamanhoArquivo,
				String acessoRestrito) {
			this.id = id;
			this.dataHoraInclusao = dataHoraInclusao;
			this.codAtendimento=codAtendimento;
			this.refAtendimento=refAtendimento.toString();
			this.seqSS=seqSS;
			this.descricao = descricao;
			this.usuario = usuario;
			this.nomeArquivo = nomeArquivo;
			this.tamanhoArquivo = tamanhoArquivo;
			this.acessoRestrito = acessoRestrito;
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
	public Integer getTamanhoArquivo() {
		return tamanhoArquivo;
	}
	public void setTamanhoArquivo(Integer tamanhoArquivo) {
		this.tamanhoArquivo = tamanhoArquivo;
	}
	public byte[] getArquivo() {
		return arquivo;
	}
	public void setArquivo(byte[] arquivo) {
		this.arquivo = arquivo;
	}
	public Integer getTotalArquivo() {
		return totalArquivo;
	}
	public void setTotalArquivo(Integer totalArquivo) {
		this.totalArquivo = totalArquivo;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getAcessoRestrito() {
		return acessoRestrito;
	}
	public void setAcessoRestrito(String acessoRestrito) {
		this.acessoRestrito = acessoRestrito;
	}
	
	public String toJson() {

		return ConvertObjectToJsonCesan.execute(this);
	}
	public String getOrigemArquivo() {
		return origemArquivo;
	}
	public void setOrigemArquivo(String origemArquivo) {
		this.origemArquivo = origemArquivo;
	}
	
	
}
