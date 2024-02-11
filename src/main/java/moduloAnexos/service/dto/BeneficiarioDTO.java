package moduloAnexos.service.dto;

import java.time.LocalDate;
import java.util.List;

import moduloAnexos.model.ClienteMotivoEspecial;
import moduloAnexos.model.TipoCliente;
import moduloAnexos.util.ConvertObjectToJsonCesan;

public class BeneficiarioDTO {

	private Integer id;

	private Integer idClassificaoImobiliaria;

	private Integer codTipoBPC;

	private Integer codTipoBeneficio;

	private Integer codOutroTipoBeneficio;

	private String nis;

	private Integer telefone1;
	
	private Short ddd1;
	
	private Integer codTipoTelefone1;

	private Integer telefone2;

	private Short ddd2;

	private Integer codTipoTelefone2;
	
	private String cpf;

	private String rg;

	private String rgOrgaoExpedidor;

	private Long cnh;
	
	private String recado1;

	private String recado2;

	private Long numeroCTPS;

	private String serieCTPS;

	private String ufCTPS;

	private String nome;

	private LocalDate dataInclusao;

	private LocalDate dataValidade;

	private Short ativo;

	private Short csBeneficio;

	private String nomeMae;

	private LocalDate dataNascimento;

	private Integer matriculaImovel;
	private Long dv;
	private LocalDate dataUltimaAtualizacao;
	private String usuario;
	
	private String nomeMaeBeneficiario;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdClassificaoImobiliaria() {
		return idClassificaoImobiliaria;
	}

	public void setIdClassificaoImobiliaria(Integer idClassificaoImobiliaria) {
		this.idClassificaoImobiliaria = idClassificaoImobiliaria;
	}

	public Integer getCodTipoBPC() {
		return codTipoBPC;
	}

	public void setCodTipoBPC(Integer codTipoBPC) {
		this.codTipoBPC = codTipoBPC;
	}

	public Integer getCodTipoBeneficio() {
		return codTipoBeneficio;
	}

	public void setCodTipoBeneficio(Integer codTipoBeneficio) {
		this.codTipoBeneficio = codTipoBeneficio;
	}

	public Integer getCodOutroTipoBeneficio() {
		return codOutroTipoBeneficio;
	}

	public void setCodOutroTipoBeneficio(Integer codOutroTipoBeneficio) {
		this.codOutroTipoBeneficio = codOutroTipoBeneficio;
	}

	public String getNis() {
		return nis;
	}

	public void setNis(String nis) {
		this.nis = nis;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getRgOrgaoExpedidor() {
		return rgOrgaoExpedidor;
	}

	public void setRgOrgaoExpedidor(String rgOrgaoExpedidor) {
		this.rgOrgaoExpedidor = rgOrgaoExpedidor;
	}

	public Long getCnh() {
		return cnh;
	}

	public void setCnh(Long cnh) {
		this.cnh = cnh;
	}

	public Long getNumeroCTPS() {
		return numeroCTPS;
	}

	public void setNumeroCTPS(Long numeroCTPS) {
		this.numeroCTPS = numeroCTPS;
	}

	public String getSerieCTPS() {
		return serieCTPS;
	}

	public void setSerieCTPS(String serieCTPS) {
		this.serieCTPS = serieCTPS;
	}

	public String getUfCTPS() {
		return ufCTPS;
	}

	public void setUfCTPS(String ufCTPS) {
		this.ufCTPS = ufCTPS;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(LocalDate dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public LocalDate getDataValidade() {
		return dataValidade;
	}

	public void setDataValidade(LocalDate dataValidade) {
		this.dataValidade = dataValidade;
	}

	public Short getAtivo() {
		return ativo;
	}

	public void setAtivo(Short ativo) {
		this.ativo = ativo;
	}

	public Short getCsBeneficio() {
		return csBeneficio;
	}

	public void setCsBeneficio(Short csBeneficio) {
		this.csBeneficio = csBeneficio;
	}

	public String getNomeMae() {
		return nomeMae;
	}

	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Integer getMatriculaImovel() {
		return matriculaImovel;
	}

	public void setMatriculaImovel(Integer matriculaImovel) {
		this.matriculaImovel = matriculaImovel;
	}

	public Long getDv() {
		return dv;
	}

	public void setDv(Long dv) {
		this.dv = dv;
	}
	
	public Integer getTelefone1() {
		return telefone1;
	}

	public void setTelefone1(Integer telefone1) {
		this.telefone1 = telefone1;
	}

	public Short getDdd1() {
		return ddd1;
	}

	public void setDdd1(Short ddd1) {
		this.ddd1 = ddd1;
	}

	public Integer getCodTipoTelefone1() {
		return codTipoTelefone1;
	}

	public void setCodTipoTelefone1(Integer codTipoTelefone1) {
		this.codTipoTelefone1 = codTipoTelefone1;
	}

	public Integer getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(Integer telefone2) {
		this.telefone2 = telefone2;
	}

	public Short getDdd2() {
		return ddd2;
	}

	public void setDdd2(Short ddd2) {
		this.ddd2 = ddd2;
	}

	
	public Integer getCodTipoTelefone2() {
		return codTipoTelefone2;
	}

	public void setCodTipoTelefone2(Integer codTipoTelefone2) {
		this.codTipoTelefone2 = codTipoTelefone2;
	}

	public String getRecado1() {
		return recado1;
	}

	public void setRecado1(String recado1) {
		this.recado1 = recado1;
	}

	public String getRecado2() {
		return recado2;
	}

	public void setRecado2(String recado2) {
		this.recado2 = recado2;
	}
	
	public LocalDate getDataUltimaAtualizacao() {
		return dataUltimaAtualizacao;
	}

	public void setDataUltimaAtualizacao(LocalDate dataUltimaAtualizacao) {
		this.dataUltimaAtualizacao = dataUltimaAtualizacao;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getNomeMaeBeneficiario() {
		return nomeMaeBeneficiario;
	}

	public void setNomeMaeBeneficiario(String nomeMaeBeneficiario) {
		this.nomeMaeBeneficiario = nomeMaeBeneficiario;
	}

	public String toJson() {

		return ConvertObjectToJsonCesan.execute(this);
	}
	
}