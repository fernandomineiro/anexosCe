package moduloAnexos.service.dto;

public class SolicitacaoServicoDTO {

	private Integer codAtendimento;
	private Integer refAtendimento;
	private Integer seqSS;
	private Long id;
    private boolean flagExclusao;
	public Integer getCodAtendimento() {
		return codAtendimento;
	}
	public void setCodAtendimento(Integer codAtendimento) {
		this.codAtendimento = codAtendimento;
	}
	public Integer getRefAtendimento() {
		return refAtendimento;
	}
	public void setRefAtendimento(Integer refAtendimento) {
		this.refAtendimento = refAtendimento;
	}
	public Integer getSeqSS() {
		return seqSS;
	}
	public void setSeqSS(Integer seqSS) {
		this.seqSS = seqSS;
	}
	public boolean isFlagExclusao() {
		return flagExclusao;
	}
	public void setFlagExclusao(boolean flagExclusao) {
		this.flagExclusao = flagExclusao;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
  
	
  
	
	
}
