package moduloAnexos.service.dto;

public class ItemAtendimentoDTO {

	private Integer codAtendimento;
	private Integer refAtendimento;
	private Integer seqAtendimento;
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

	public Integer getSeqAtendimento() {
		return seqAtendimento;
	}

	public void setSeqAtendimento(Integer seqAtendimento) {
		this.seqAtendimento = seqAtendimento;
	}

	public boolean isFlagExclusao() {
		return flagExclusao;
	}

	public void setFlagExclusao(boolean flagExclusao) {
		this.flagExclusao = flagExclusao;
	}

}
