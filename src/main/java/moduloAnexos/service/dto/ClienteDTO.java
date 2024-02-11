package moduloAnexos.service.dto;

import java.time.LocalDate;
import java.util.List;

import moduloAnexos.model.ClienteMotivoEspecial;
import moduloAnexos.model.TipoCliente;
import moduloAnexos.util.ConvertObjectToJsonCesan;

public class ClienteDTO {

	private Integer cdCliente;
	private String nome;
	private Integer cdAtendimento;
	private Short cdBairro;
	private Short cdCidade;
	private ClienteDTO clientePrincipal;
	private Integer cdClientePrincipal;
	private Short cdLogradouro;
	private Integer cdRg;
	private Integer cep;
	private String complEndereco;
	private String cpfCnpj;
	private Short ddd;
	private Integer dtInclusao;
	private Short dv;
	private Short dvCp;
	private String faturaAgrupada;
	private String idUsuario;
	private String maint;
	private String nomeClientePrinc;
	private Integer numEndereco;
	private Integer refAtendimento;
	private String reterImpostos;
	private Short seqSs;
	private String siafi;
	private Integer telefone;
	private TipoCliente tipoCliente;
	private Short tpDocumento;
	private String nomeMae;
	private Integer dtNascimento;
	private String deficienciaVisual;
	private Short tipoTel;
	private Short dddC;
	private Integer telefoneC;
	private Short tipoTelC;
	private String email;
	private String rg;
	private String orgaoRg;
	private Long cnh;
	private Integer ctps;
	private String pesRecado1;
	private String pesRecado2;
	private String ctpsSerie;
	private String ctpsUf;
	private String validado;
	private String idRespValidacao;
	private Integer dtValidacao;
	private Short ddd3;
	private Short ddd4;
	private Integer dtAtualizacao;
	private String respAtualizacao;
	private String receberSms;
	private Integer telefone3;
	private Integer telefone4;
	private Short tipoTel3;
	private Short tipoTel4;
	private Long nis;
	private String pesRecado3;
	private String pesRecado4;
	private String localidadeCorresp;
	private String bairroCorresp;
	private String logradouroCorresp;
	private Integer numEndCorresp;
	private String complEndCorresp;
	private String estadoCorresp;
	private Integer cepCorresp;
	private String nomeReferencia;
	private String obito;
	private LocalDate dtObito;
	private String clienteEspecial;
	private String nomeConjuge;
	private String nomeSocial;
	private EnderecoSituacaoAguaEsgotoDTO endereco;
	private String justificativa;
	private List<ClienteMotivoEspecial> motivoClienteEspecial;
	private String tpPessoa;
	
	public String getCodigoClienteFormatado() {
		return "0".repeat(7 - cdCliente.toString().length()) + cdCliente + "-" + dv;
	}
	
	public Integer getCdCliente() {
		return cdCliente;
	}

	public void setCdCliente(Integer cdCliente) {
		this.cdCliente = cdCliente;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getCdAtendimento() {
		return cdAtendimento;
	}

	public void setCdAtendimento(Integer cdAtendimento) {
		this.cdAtendimento = cdAtendimento;
	}

	public Short getCdBairro() {
		return cdBairro;
	}

	public void setCdBairro(Short cdBairro) {
		this.cdBairro = cdBairro;
	}

	public Short getCdCidade() {
		return cdCidade;
	}

	public void setCdCidade(Short cdCidade) {
		this.cdCidade = cdCidade;
	}

	public Short getCdLogradouro() {
		return cdLogradouro;
	}

	public void setCdLogradouro(Short cdLogradouro) {
		this.cdLogradouro = cdLogradouro;
	}

	public Integer getCdRg() {
		return cdRg;
	}

	public void setCdRg(Integer cdRg) {
		this.cdRg = cdRg;
	}

	public Integer getCep() {
		return cep;
	}

	public void setCep(Integer cep) {
		this.cep = cep;
	}

	public String getComplEndereco() {
		return complEndereco;
	}

	public void setComplEndereco(String complEndereco) {
		this.complEndereco = complEndereco;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public Short getDdd() {
		return ddd;
	}

	public void setDdd(Short ddd) {
		this.ddd = ddd;
	}

	public Integer getDtInclusao() {
		return dtInclusao;
	}

	public void setDtInclusao(Integer dtInclusao) {
		this.dtInclusao = dtInclusao;
	}

	public Short getDv() {
		return dv;
	}

	public void setDv(Short dv) {
		this.dv = dv;
	}

	public Short getDvCp() {
		return dvCp;
	}

	public void setDvCp(Short dvCp) {
		this.dvCp = dvCp;
	}

	public String getFaturaAgrupada() {
		return faturaAgrupada;
	}

	public void setFaturaAgrupada(String faturaAgrupada) {
		this.faturaAgrupada = faturaAgrupada;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getMaint() {
		return maint;
	}

	public void setMaint(String maint) {
		this.maint = maint;
	}

	public String getNomeClientePrinc() {
		return nomeClientePrinc;
	}

	public void setNomeClientePrinc(String nomeClientePrinc) {
		this.nomeClientePrinc = nomeClientePrinc;
	}

	public Integer getNumEndereco() {
		return numEndereco;
	}

	public void setNumEndereco(Integer numEndereco) {
		this.numEndereco = numEndereco;
	}

	public Integer getRefAtendimento() {
		return refAtendimento;
	}

	public void setRefAtendimento(Integer refAtendimento) {
		this.refAtendimento = refAtendimento;
	}

	public String getReterImpostos() {
		return reterImpostos;
	}

	public void setReterImpostos(String reterImpostos) {
		this.reterImpostos = reterImpostos;
	}

	public Short getSeqSs() {
		return seqSs;
	}

	public void setSeqSs(Short seqSs) {
		this.seqSs = seqSs;
	}

	public String getSiafi() {
		return siafi;
	}

	public void setSiafi(String siafi) {
		this.siafi = siafi;
	}

	public Integer getTelefone() {
		return telefone;
	}

	public void setTelefone(Integer telefone) {
		this.telefone = telefone;
	}

	public Short getTpDocumento() {
		return tpDocumento;
	}

	public void setTpDocumento(Short tpDocumento) {
		this.tpDocumento = tpDocumento;
	}

	public String getNomeMae() {
		return nomeMae;
	}

	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}

	public Integer getDtNascimento() {
		return dtNascimento;
	}

	public void setDtNascimento(Integer dtNascimento) {
		this.dtNascimento = dtNascimento;
	}

	public String getDeficienciaVisual() {
		return deficienciaVisual;
	}

	public void setDeficienciaVisual(String deficienciaVisual) {
		this.deficienciaVisual = deficienciaVisual;
	}

	public Short getTipoTel() {
		return tipoTel;
	}

	public void setTipoTel(Short tipoTel) {
		this.tipoTel = tipoTel;
	}

	public Short getDddC() {
		return dddC;
	}

	public void setDddC(Short dddC) {
		this.dddC = dddC;
	}

	public Integer getTelefoneC() {
		return telefoneC;
	}

	public void setTelefoneC(Integer telefoneC) {
		this.telefoneC = telefoneC;
	}

	public Short getTipoTelC() {
		return tipoTelC;
	}

	public void setTipoTelC(Short tipoTelC) {
		this.tipoTelC = tipoTelC;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getOrgaoRg() {
		return orgaoRg;
	}

	public void setOrgaoRg(String orgaoRg) {
		this.orgaoRg = orgaoRg;
	}

	public Long getCnh() {
		return cnh;
	}

	public void setCnh(Long cnh) {
		this.cnh = cnh;
	}

	public Integer getCtps() {
		return ctps;
	}

	public void setCtps(Integer ctps) {
		this.ctps = ctps;
	}

	public String getPesRecado1() {
		return pesRecado1;
	}

	public void setPesRecado1(String pesRecado1) {
		this.pesRecado1 = pesRecado1;
	}

	public String getPesRecado2() {
		return pesRecado2;
	}

	public void setPesRecado2(String pesRecado2) {
		this.pesRecado2 = pesRecado2;
	}

	public String getCtpsSerie() {
		return ctpsSerie;
	}

	public void setCtpsSerie(String ctpsSerie) {
		this.ctpsSerie = ctpsSerie;
	}

	public String getCtpsUf() {
		return ctpsUf;
	}

	public void setCtpsUf(String ctpsUf) {
		this.ctpsUf = ctpsUf;
	}

	public String getValidado() {
		return validado;
	}

	public void setValidado(String validado) {
		this.validado = validado;
	}

	public String getIdRespValidacao() {
		return idRespValidacao;
	}

	public void setIdRespValidacao(String idRespValidacao) {
		this.idRespValidacao = idRespValidacao;
	}

	public Integer getDtValidacao() {
		return dtValidacao;
	}

	public void setDtValidacao(Integer dtValidacao) {
		this.dtValidacao = dtValidacao;
	}

	public Short getDdd3() {
		return ddd3;
	}

	public void setDdd3(Short ddd3) {
		this.ddd3 = ddd3;
	}

	public Short getDdd4() {
		return ddd4;
	}

	public void setDdd4(Short ddd4) {
		this.ddd4 = ddd4;
	}

	public Integer getDtAtualizacao() {
		return dtAtualizacao;
	}

	public void setDtAtualizacao(Integer dtAtualizacao) {
		this.dtAtualizacao = dtAtualizacao;
	}

	public String getRespAtualizacao() {
		return respAtualizacao;
	}

	public void setRespAtualizacao(String respAtualizacao) {
		this.respAtualizacao = respAtualizacao;
	}

	public String getReceberSms() {
		return receberSms;
	}

	public void setReceberSms(String receberSms) {
		this.receberSms = receberSms;
	}

	public Integer getTelefone3() {
		return telefone3;
	}

	public void setTelefone3(Integer telefone3) {
		this.telefone3 = telefone3;
	}

	public Integer getTelefone4() {
		return telefone4;
	}

	public void setTelefone4(Integer telefone4) {
		this.telefone4 = telefone4;
	}

	public Short getTipoTel3() {
		return tipoTel3;
	}

	public void setTipoTel3(Short tipoTel3) {
		this.tipoTel3 = tipoTel3;
	}

	public Short getTipoTel4() {
		return tipoTel4;
	}

	public void setTipoTel4(Short tipoTel4) {
		this.tipoTel4 = tipoTel4;
	}

	public Long getNis() {
		return nis;
	}

	public void setNis(Long nis) {
		this.nis = nis;
	}

	public String getPesRecado3() {
		return pesRecado3;
	}

	public void setPesRecado3(String pesRecado3) {
		this.pesRecado3 = pesRecado3;
	}

	public String getPesRecado4() {
		return pesRecado4;
	}

	public void setPesRecado4(String pesRecado4) {
		this.pesRecado4 = pesRecado4;
	}

	public String getLocalidadeCorresp() {
		return localidadeCorresp;
	}

	public void setLocalidadeCorresp(String localidadeCorresp) {
		this.localidadeCorresp = localidadeCorresp;
	}

	public String getBairroCorresp() {
		return bairroCorresp;
	}

	public void setBairroCorresp(String bairroCorresp) {
		this.bairroCorresp = bairroCorresp;
	}

	public String getLogradouroCorresp() {
		return logradouroCorresp;
	}

	public void setLogradouroCorresp(String logradouroCorresp) {
		this.logradouroCorresp = logradouroCorresp;
	}

	public Integer getNumEndCorresp() {
		return numEndCorresp;
	}

	public void setNumEndCorresp(Integer numEndCorresp) {
		this.numEndCorresp = numEndCorresp;
	}

	public String getComplEndCorresp() {
		return complEndCorresp;
	}

	public void setComplEndCorresp(String complEndCorresp) {
		this.complEndCorresp = complEndCorresp;
	}

	public String getEstadoCorresp() {
		return estadoCorresp;
	}

	public void setEstadoCorresp(String estadoCorresp) {
		this.estadoCorresp = estadoCorresp;
	}

	public Integer getCepCorresp() {
		return cepCorresp;
	}

	public void setCepCorresp(Integer cepCorresp) {
		this.cepCorresp = cepCorresp;
	}

	public String getObito() {
		return obito;
	}

	public void setObito(String obito) {
		this.obito = obito;
	}

	public String getClienteEspecial() {
		return clienteEspecial;
	}

	public void setClienteEspecial(String clienteEspecial) {
		this.clienteEspecial = clienteEspecial;
	}

	public TipoCliente getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(TipoCliente tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	public String getNomeConjuge() {
		return nomeConjuge;
	}

	public void setNomeConjuge(String nomeConjuge) {
		this.nomeConjuge = nomeConjuge;
	}

	public String getNomeSocial() {
		return nomeSocial;
	}

	public void setNomeSocial(String nomeSocial) {
		this.nomeSocial = nomeSocial;
	}

	public ClienteDTO getClientePrincipal() {
		return clientePrincipal;
	}

	public void setClientePrincipal(ClienteDTO clientePrincipal) {
		this.clientePrincipal = clientePrincipal;
	}

	public EnderecoSituacaoAguaEsgotoDTO getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoSituacaoAguaEsgotoDTO endereco) {
		this.endereco = endereco;
	}

	public Integer getCdClientePrincipal() {
		return cdClientePrincipal;
	}

	public void setCdClientePrincipal(Integer cdClientePrincipal) {
		this.cdClientePrincipal = cdClientePrincipal;
	}
	
	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	public List<ClienteMotivoEspecial> getMotivoClienteEspecial() {
		return motivoClienteEspecial;
	}

	public void setMotivoClienteEspecial(List<ClienteMotivoEspecial> motivoClienteEspecial) {
		this.motivoClienteEspecial = motivoClienteEspecial;
	}

	public String getTpPessoa() {
		return tpPessoa;
	}

	public void setTpPessoa(String tpPessoa) {
		this.tpPessoa = tpPessoa;
	}

	public LocalDate getDtObito() {
		return dtObito;
	}

	public void setDtObito(LocalDate dtObito) {
		this.dtObito = dtObito;
	}

	public String getNomeReferencia() {
		return nomeReferencia;
	}

	public void setNomeReferencia(String nomeReferencia) {
		this.nomeReferencia = nomeReferencia;
	}

	public String toJson() {
		return ConvertObjectToJsonCesan.execute(this);
	}
}
