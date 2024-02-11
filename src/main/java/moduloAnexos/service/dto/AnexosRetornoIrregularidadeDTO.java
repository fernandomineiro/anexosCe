package moduloAnexos.service.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import moduloAnexos.util.ConvertObjectToJsonCesan;
import moduloAnexos.util.customAnnotation.JsonCesanNotSerializable;

public class AnexosRetornoIrregularidadeDTO {
 
	private Long id;
	@JsonCesanNotSerializable
	private LocalDateTime dataHoraInclusao;
	private Integer matricula;
	private Integer referencia;
	private Integer numPesquisa;
	private String usuario;
	private String nomeArquivo;
	@JsonIgnore
	private Integer tamanhoArquivo;
	@JsonCesanNotSerializable
	private Integer totalArquivo;
	private byte[] data;

	public AnexosRetornoIrregularidadeDTO(Long id, LocalDateTime dataHoraInclusao, Integer matricula,
			Integer referencia, Integer numPesquisa, String usuario, String nomeArquivo, Integer tamanhoArquivo,
			Integer totalArquivo, byte[] data) {
		super();
		this.id = id;
		this.dataHoraInclusao = dataHoraInclusao;
		this.matricula = matricula;
		this.referencia = referencia;
		this.numPesquisa = numPesquisa;
		this.usuario = usuario;
		this.nomeArquivo = nomeArquivo;
		this.tamanhoArquivo = tamanhoArquivo;
		this.totalArquivo = totalArquivo;
		this.data = data;
	}

	public AnexosRetornoIrregularidadeDTO() {
		super();
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
	
	public Integer getMatricula() {
		return matricula;
	}
	
	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
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
	
	public Integer getTamanhoArquivo() {
		return tamanhoArquivo;
	}
	
	public void setTamanhoArquivo(Integer tamanhoArquivo) {
		this.tamanhoArquivo = tamanhoArquivo;
	}
	
	public Integer getTotalArquivo() {
		return totalArquivo;
	}
	
	public void setTotalArquivo(Integer totalArquivo) {
		this.totalArquivo = totalArquivo;
	}
	
	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String toJson() {
		return ConvertObjectToJsonCesan.execute(this);
	}
}
