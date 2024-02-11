package moduloAnexos.service.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EnderecoSituacaoAguaEsgotoDTO {

	@Id
	private Integer matriculaImovel;
	private Integer cdCliente;
	private String siglaLogradouro;
	private String dcLogradouro;
	private Integer numEndereco;
	private String dcBairro;
	private String dcCidade;
	private String sitLigacaoAgua;
	private String sitLigacaoEsgoto;
	private String nomeTitular;
	private String nomeProprietario;
	
	public String getEndereço() {
		return siglaLogradouro + " " + dcLogradouro.trim() + ", " + numEndereco + ", " + dcBairro + ", " + dcCidade;
	}
	
	public Integer getMatriculaImovel() {
		return matriculaImovel;
	}
	public void setMatriculaImovel(Integer matriculaImovel) {
		this.matriculaImovel = matriculaImovel;
	}
	public Integer getCdCliente() {
		return cdCliente;
	}
	public void setCdCliente(Integer cdCliente) {
		this.cdCliente = cdCliente;
	}
	public String getSiglaLogradouro() {
		return siglaLogradouro;
	}
	public void setSiglaLogradouro(String siglaLogradouro) {
		this.siglaLogradouro = siglaLogradouro;
	}
	public String getDcLogradouro() {
		return dcLogradouro;
	}
	public void setDcLogradouro(String dcLogradouro) {
		this.dcLogradouro = dcLogradouro;
	}
	public Integer getNumEndereco() {
		return numEndereco;
	}
	public void setNumEndereco(Integer numEndereco) {
		this.numEndereco = numEndereco;
	}
	public String getDcBairro() {
		return dcBairro;
	}
	public void setDcBairro(String dcBairro) {
		this.dcBairro = dcBairro;
	}
	public String getDcCidade() {
		return dcCidade;
	}
	public void setDcCidade(String dcCidade) {
		this.dcCidade = dcCidade;
	}
	public String getSitLigacaoAgua() {
		return sitLigacaoAgua;
	}
	public void setSitLigacaoAgua(String sitLigacaoAgua) {
		this.sitLigacaoAgua = sitLigacaoAgua;
	}
	public String getSitLigacaoEsgoto() {
		return sitLigacaoEsgoto;
	}
	public void setSitLigacaoEsgoto(String sitLigacaoEsgoto) {
		this.sitLigacaoEsgoto = sitLigacaoEsgoto;
	}
	public String getNomeTitular() {
		return nomeTitular;
	}
	public void setNomeTitular(String nomeTitular) {
		this.nomeTitular = nomeTitular;
	}
	public String getNomeProprietario() {
		return nomeProprietario;
	}
	public void setNomeProprietario(String nomeProprietario) {
		this.nomeProprietario = nomeProprietario;
	}
	
}
