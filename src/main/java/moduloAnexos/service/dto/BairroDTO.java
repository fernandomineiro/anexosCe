package moduloAnexos.service.dto;

import moduloAnexos.util.ConvertObjectToJsonCesan;
import moduloAnexos.util.customAnnotation.JsonCesanNotSerializable;

public class BairroDTO {
	
	private Short cdBairro;
	private Short cdLocalidade;
	private String nomeBairro;
	private Integer cdMunicipioImpressao;
	
	private String nomeLocalidade;
	
	@JsonCesanNotSerializable
	private Integer id;
	
	public BairroDTO() {
	}

	public BairroDTO(Short cdBairro, Short cdLocalidade, String nomeBairro, Integer id) {
		super();
		this.cdBairro = cdBairro;
		this.cdLocalidade = cdLocalidade;
		this.nomeBairro = nomeBairro;
		this.id = id;
	}

	public Short getCdBairro() {
		return cdBairro;
	}

	public void setCdBairro(Short cdBairro) {
		this.cdBairro = cdBairro;
	}

	public Short getCdLocalidade() {
		return cdLocalidade;
	}

	public void setCdLocalidade(Short cdLocalidade) {
		this.cdLocalidade = cdLocalidade;
	}

	public String getNomeBairro() {
		return nomeBairro;
	}

	public void setNomeBairro(String nomeBairro) {
		this.nomeBairro = nomeBairro;
	}

	public Integer getCdMunicipioImpressao() {
		return cdMunicipioImpressao;
	}

	public void setCdMunicipioImpressao(Integer cdMunicipioImpressao) {
		this.cdMunicipioImpressao = cdMunicipioImpressao;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNomeLocalidade() {
		return nomeLocalidade;
	}

	public void setNomeLocalidade(String nomeLocalidade) {
		this.nomeLocalidade = nomeLocalidade;
	}

	public String toJson() {
		return ConvertObjectToJsonCesan.execute(this);
	}

}
