package moduloAnexos.service.dto;

import java.util.List;

public class AnexosFiscalizacaoWrapperDTO {

	private List<AnexosFiscalizacaoListDTO> listAnexosSolicitacaoServicoDTO;
	private long totalRegistro;
	
	public List<AnexosFiscalizacaoListDTO> getListAnexosSolicitacaoServicoDTO() {
		return listAnexosSolicitacaoServicoDTO;
	}
	public void setListAnexosSolicitacaoServicoDTO(List<AnexosFiscalizacaoListDTO> listAnexosSolicitacaoServicoDTO) {
		this.listAnexosSolicitacaoServicoDTO = listAnexosSolicitacaoServicoDTO;
	}
	public long getTotalRegistro() {
		return totalRegistro;
	}
	public void setTotalRegistro(long totalRegistro) {
		this.totalRegistro = totalRegistro;
	}
	
	
}
