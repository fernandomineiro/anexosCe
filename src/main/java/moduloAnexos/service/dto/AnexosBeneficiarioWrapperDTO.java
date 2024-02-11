package moduloAnexos.service.dto;

import java.util.List;

public class AnexosBeneficiarioWrapperDTO {

	private List<AnexosBeneficiarioDTO> listAnexosBeneficiarioDTO;

	private Long totalRegistros;
	
	private Integer beneficiarioExcluido = 0;
	private Integer beneficiarioValidado  = 0;
	private Integer beneficiarioEspecial  = 0;
	private Integer beneficiarioPublico  = 0;
	private String nomeBeneficiario;
	
	public List<AnexosBeneficiarioDTO> getListAnexosBeneficiarioDTO() {
		return listAnexosBeneficiarioDTO;
	}
	
	public void setListAnexosBeneficiarioDTO(List<AnexosBeneficiarioDTO> listAnexosBeneficiarioDTO) {
		this.listAnexosBeneficiarioDTO = listAnexosBeneficiarioDTO;
	}
	
	public Long getTotalRegistros() {
		return totalRegistros;
	}
	
	public void setTotalRegistros(Long totalRegistros) {
		this.totalRegistros = totalRegistros;
	}
	
	public Integer getBeneficiarioExcluido() {
		return beneficiarioExcluido;
	}
	
	public void setBeneficiarioExcluido(Integer beneficiarioExcluido) {
		this.beneficiarioExcluido = beneficiarioExcluido;
	}
	
	public Integer getBeneficiarioValidado() {
		return beneficiarioValidado;
	}
	
	public void setBeneficiarioValidado(Integer beneficiarioValidado) {
		this.beneficiarioValidado = beneficiarioValidado;
	}
	
	public Integer getBeneficiarioEspecial() {
		return beneficiarioEspecial;
	}
	
	public void setBeneficiarioEspecial(Integer beneficiarioEspecial) {
		this.beneficiarioEspecial = beneficiarioEspecial;
	}
	
	public Integer getBeneficiarioPublico() {
		return beneficiarioPublico;
	}
	
	public void setBeneficiarioPublico(Integer beneficiarioPublico) {
		this.beneficiarioPublico = beneficiarioPublico;
	}
	
	public String getNomeBeneficiario() {
		return nomeBeneficiario;
	}
	
	public void setNomeBeneficiario(String nomeBeneficiario) {
		this.nomeBeneficiario = nomeBeneficiario;
	}



}
