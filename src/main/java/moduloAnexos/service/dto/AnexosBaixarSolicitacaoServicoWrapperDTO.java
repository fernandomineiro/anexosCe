package moduloAnexos.service.dto;

import java.util.List;

public class AnexosBaixarSolicitacaoServicoWrapperDTO {

	private List<AnexosBaixarSolicitacaoServicoDTO> listAnexosBaixarSolicitacaoServicoDTO;
	private Integer totalArquivo;
	private long totalRegistro;


	public Integer getTotalArquivo() {
		return totalArquivo;
	}

	public void setTotalArquivo(Integer totalArquivo) {
		this.totalArquivo = totalArquivo;
	}

	public long getTotalRegistro() {
		return totalRegistro;
	}

	public void setTotalRegistro(long totalRegistro) {
		this.totalRegistro = totalRegistro;
	}

	public List<AnexosBaixarSolicitacaoServicoDTO> getListAnexosBaixarSolicitacaoServicoDTO() {
		return listAnexosBaixarSolicitacaoServicoDTO;
	}

	public void setListAnexosBaixarSolicitacaoServicoDTO(List<AnexosBaixarSolicitacaoServicoDTO> listAnexosBaixarSolicitacaoServicoDTO) {
		this.listAnexosBaixarSolicitacaoServicoDTO = listAnexosBaixarSolicitacaoServicoDTO;
	}

}
