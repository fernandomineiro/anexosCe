package moduloAnexos.service.dto;

import java.time.LocalDateTime;
import java.util.List;

import moduloAnexos.model.TipoOcorrenciaOperacional;

public class OcorrenciaOperacionalDTO {

	private Short cdCidade;
	
	private String nomeCidade;
	
	private Long id;
	
	private List<OcorrenciaOperacionalBairroDTO> bairros;
	
	private String areasAfetadas;
	
	private String idUsuarioAlteracao;
	
	private String idUsuarioInclusao;
	
	private String maint;
	
	private String motivoOco;
	
	private String observacao;
	
	private LocalDateTime dataHoraExclusao;
	
	private String idUsuarioExclusao;

	private TipoOcorrenciaOperacional tpOcorrenciaOperacional;
	
	private TipoAreaProcessoDTO tpAreaProcesso;
	
	private LocalDateTime dataHoraInclusao;
	private LocalDateTime dataHoraInicio;
	private LocalDateTime dataHoraAlteracao;
	private LocalDateTime dataHoraNormalizacao;
	private LocalDateTime dataHoraPrevisao;
	
	private Integer numLigacoes;
	
	private Integer numEconomias;
	
	private Integer numPopulacaoAfetada;
	
	private String motivoOcoDivulgacao;
	
	private List<MunicipioLocalidadeDTO> localidades;
	
	public Short getCdCidade() {
		return cdCidade;
	}
	public void setCdCidade(Short cdCidade) {
		this.cdCidade = cdCidade;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<OcorrenciaOperacionalBairroDTO> getBairros() {
		return bairros;
	}
	public void setBairros(List<OcorrenciaOperacionalBairroDTO> bairros) {
		this.bairros = bairros;
	}
	public String getAreasAfetadas() {
		return areasAfetadas;
	}
	public void setAreasAfetadas(String areasAfetadas) {
		this.areasAfetadas = areasAfetadas;
	}
	public String getIdUsuarioAlteracao() {
		return idUsuarioAlteracao;
	}
	public void setIdUsuarioAlteracao(String idUsuarioAlteracao) {
		this.idUsuarioAlteracao = idUsuarioAlteracao;
	}
	public String getIdUsuarioInclusao() {
		return idUsuarioInclusao;
	}
	public void setIdUsuarioInclusao(String idUsuarioInclusao) {
		this.idUsuarioInclusao = idUsuarioInclusao;
	}
	public String getMaint() {
		return maint;
	}
	public void setMaint(String maint) {
		this.maint = maint;
	}
	public String getMotivoOco() {
		return motivoOco;
	}
	public void setMotivoOco(String motivoOco) {
		this.motivoOco = motivoOco;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public LocalDateTime getDataHoraExclusao() {
		return dataHoraExclusao;
	}
	public void setDataHoraExclusao(LocalDateTime dataHoraExclusao) {
		this.dataHoraExclusao = dataHoraExclusao;
	}
	public String getIdUsuarioExclusao() {
		return idUsuarioExclusao;
	}
	public void setIdUsuarioExclusao(String idUsuarioExclusao) {
		this.idUsuarioExclusao = idUsuarioExclusao;
	}
	public TipoOcorrenciaOperacional getTpOcorrenciaOperacional() {
		return tpOcorrenciaOperacional;
	}
	public void setTpOcorrenciaOperacional(TipoOcorrenciaOperacional tpOcorrenciaOperacional) {
		this.tpOcorrenciaOperacional = tpOcorrenciaOperacional;
	}
	public LocalDateTime getDataHoraInclusao() {
		return dataHoraInclusao;
	}
	public void setDataHoraInclusao(LocalDateTime dataHoraInclusao) {
		this.dataHoraInclusao = dataHoraInclusao;
	}
	public LocalDateTime getDataHoraInicio() {
		return dataHoraInicio;
	}
	public void setDataHoraInicio(LocalDateTime dataHoraInicio) {
		this.dataHoraInicio = dataHoraInicio;
	}
	public LocalDateTime getDataHoraAlteracao() {
		return dataHoraAlteracao;
	}
	public void setDataHoraAlteracao(LocalDateTime dataHoraAlteracao) {
		this.dataHoraAlteracao = dataHoraAlteracao;
	}
	public LocalDateTime getDataHoraNormalizacao() {
		return dataHoraNormalizacao;
	}
	public void setDataHoraNormalizacao(LocalDateTime dataHoraNormalizacao) {
		this.dataHoraNormalizacao = dataHoraNormalizacao;
	}
	public LocalDateTime getDataHoraPrevisao() {
		return dataHoraPrevisao;
	}
	public void setDataHoraPrevisao(LocalDateTime dataHoraPrevisao) {
		this.dataHoraPrevisao = dataHoraPrevisao;
	}
	public Integer getNumLigacoes() {
		return numLigacoes;
	}
	public void setNumLigacoes(Integer numLigacoes) {
		this.numLigacoes = numLigacoes;
	}
	public Integer getNumEconomias() {
		return numEconomias;
	}
	public void setNumEconomias(Integer numEconomias) {
		this.numEconomias = numEconomias;
	}
	public Integer getNumPopulacaoAfetada() {
		return numPopulacaoAfetada;
	}
	public void setNumPopulacaoAfetada(Integer numPopulacaoAfetada) {
		this.numPopulacaoAfetada = numPopulacaoAfetada;
	}
	public String getNomeCidade() {
		return nomeCidade;
	}
	public void setNomeCidade(String nomeCidade) {
		this.nomeCidade = nomeCidade;
	}
	public TipoAreaProcessoDTO getTpAreaProcesso() {
		return tpAreaProcesso;
	}
	public void setTpAreaProcesso(TipoAreaProcessoDTO tpAreaProcesso) {
		this.tpAreaProcesso = tpAreaProcesso;
	}
	public String getMotivoOcoDivulgacao() {
		return motivoOcoDivulgacao;
	}
	public void setMotivoOcoDivulgacao(String motivoOcoDivulgacao) {
		this.motivoOcoDivulgacao = motivoOcoDivulgacao;
	}
	public List<MunicipioLocalidadeDTO> getLocalidades() {
		return localidades;
	}
	public void setLocalidades(List<MunicipioLocalidadeDTO> localidades) {
		this.localidades = localidades;
	}

}