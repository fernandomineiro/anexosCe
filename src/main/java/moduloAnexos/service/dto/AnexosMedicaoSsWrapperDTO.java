package moduloAnexos.service.dto;

import java.util.List;

public class AnexosMedicaoSsWrapperDTO {

	private List<AnexosMedicaoSsDTO> listAnexosSolicitacaoServicoDTO;
	private long totalRegistro;
	
	public List<AnexosMedicaoSsDTO> getListAnexosSolicitacaoServicoDTO() {
		return listAnexosSolicitacaoServicoDTO;
	}
	public void setListAnexosSolicitacaoServicoDTO(List<AnexosMedicaoSsDTO> listAnexosSolicitacaoServicoDTO) {
		this.listAnexosSolicitacaoServicoDTO = listAnexosSolicitacaoServicoDTO;
	}
	public long getTotalRegistro() {
		return totalRegistro;
	}
	public void setTotalRegistro(long totalRegistro) {
		this.totalRegistro = totalRegistro;
	}
	
	
}
