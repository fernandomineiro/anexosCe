package moduloAnexos.service.dto;

import java.util.List;

public class AnexosSolicitacaoServicoWrapperDTO {

	private List<AnexosSolicitacaoServicoDTO> listAnexosSolicitacaoServicoDTO;
	private Integer totalArquivo;
	private long totalRegistro;


	public List<AnexosSolicitacaoServicoDTO> getListAnexosSolicitacaoServicoDTO() {
		return listAnexosSolicitacaoServicoDTO;
	}

	public void setListAnexosSolicitacaoServicoDTO(List<AnexosSolicitacaoServicoDTO> listAnexosSolicitacaoServicoDTO) {
		this.listAnexosSolicitacaoServicoDTO = listAnexosSolicitacaoServicoDTO;
	}

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

}
