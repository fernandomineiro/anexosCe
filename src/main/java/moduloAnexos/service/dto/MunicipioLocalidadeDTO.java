package moduloAnexos.service.dto;

import java.util.Date;


public class MunicipioLocalidadeDTO {
	
	private Short cdCidade;	
	
	private String dcCidade;

	private Short cdMunicipio;
	
	private Date dataHoraExclusao;
	
	public Short getCdCidade() {
		return cdCidade;
	}
	public void setCdCidade(Short cdCidade) {
		this.cdCidade = cdCidade;
	}
	public String getDcCidade() {
		return dcCidade;
	}
	public void setDcCidade(String dcCidade) {
		this.dcCidade = dcCidade;
	}
	public Date getDataHoraExclusao() {
		return dataHoraExclusao;
	}
	public void setDataHoraExclusao(Date dataHoraExclusao) {
		this.dataHoraExclusao = dataHoraExclusao;
	}

	public Short getCdMunicipio() {
		return cdMunicipio;
	}
	public void setCdMunicipio(Short cdMunicipio) {
		this.cdMunicipio = cdMunicipio;
	}
	@Override
	public String toString() {
		return this.cdCidade.toString();
	}
	
	
}
