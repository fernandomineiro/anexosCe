
package moduloAnexos.service.dto;
import java.time.LocalDateTime;
import moduloAnexos.util.ConvertObjectToJsonCesan;

public class AnexosFiscalizacaoListDTO {

	private Long id;

	private Integer idFiscalizacoesSS;
	
	private LocalDateTime dataHoraInclusao;

	private Integer cdAtendimento;
	
	private Integer refAtendimento;

	private Short seqSS;

	private String descricao;

	private String usuario;

	private String nomeArquivo;

	private byte[] arquivo;

	private Integer tamanhoArquivo;

	private String acessoRestrito;
	


	public AnexosFiscalizacaoListDTO(Long id, Integer idFiscalizacoesSS, LocalDateTime dataHoraInclusao,
			Integer cdAtendimento, Integer refAtendimento, Short seqSS, String descricao, String usuario,
			String nomeArquivo, byte[] arquivo, Integer tamanhoArquivo, String acessoRestrito) {
		super();
		this.id = id;
		this.idFiscalizacoesSS = idFiscalizacoesSS;
		this.dataHoraInclusao = dataHoraInclusao;
		this.cdAtendimento = cdAtendimento;
		this.refAtendimento = refAtendimento;
		this.seqSS = seqSS;
		this.descricao = descricao;
		this.usuario = usuario;
		this.nomeArquivo = nomeArquivo;
		this.arquivo = arquivo;
		this.tamanhoArquivo = tamanhoArquivo;
		this.acessoRestrito = acessoRestrito;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getIdFiscalizacoesSS() {
		return idFiscalizacoesSS;
	}

	public void setIdFiscalizacoesSS(Integer idFiscalizacoesSS) {
		this.idFiscalizacoesSS = idFiscalizacoesSS;
	}

	public LocalDateTime getDataHoraInclusao() {
		return dataHoraInclusao;
	}


	public void setDataHoraInclusao(LocalDateTime dataHoraInclusao) {
		this.dataHoraInclusao = dataHoraInclusao;
	}

	public Integer getCdAtendimento() {
		return cdAtendimento;
	}

	public void setCdAtendimento(Integer cdAtendimento) {
		this.cdAtendimento = cdAtendimento;
	}

	public Integer getRefAtendimento() {
		return refAtendimento;
	}

	public void setRefAtendimento(Integer refAtendimento) {
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

	public byte[] getArquivo() {
		return arquivo;
	}

	public void setArquivo(byte[] arquivo) {
		this.arquivo = arquivo;
	}

	public Integer getTamanhoArquivo() {
		return tamanhoArquivo;
	}

	public void setTamanhoArquivo(Integer tamanhoArquivo) {
		this.tamanhoArquivo = tamanhoArquivo;
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
}
