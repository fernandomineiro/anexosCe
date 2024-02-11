package moduloAnexos.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import moduloAnexos.util.ConvertObjectToJsonCesan;

@Embeddable
public class IdOcorrenciaOperacional implements Serializable {

	private static final long serialVersionUID = 1L;

	private Short cdCidade;
	
	private Integer dtInicio;
	
	private Short hrInicio;
	
	private Short minInicio;

	public Short getCdCidade() {
		return cdCidade;
	}

	public void setCdCidade(Short cdCidade) {
		this.cdCidade = cdCidade;
	}

	public Integer getDtInicio() {
		return dtInicio;
	}

	public void setDtInicio(Integer dtInicio) {
		this.dtInicio = dtInicio;
	}

	public Short getHrInicio() {
		return hrInicio;
	}

	public void setHrInicio(Short hrInicio) {
		this.hrInicio = hrInicio;
	}

	public Short getMinInicio() {
		return minInicio;
	}

	public void setMinInicio(Short minInicio) {
		this.minInicio = minInicio;
	}

	public String toJson() {
		return ConvertObjectToJsonCesan.execute(this);
	}

}