package moduloAnexos.service.dto;

import java.util.List;

public class AnexosImovelWrapperDTO {

	private List<AnexosImovelDTO> listAnexosImovelDTO;

	private Long totalRegistros;

	public List<AnexosImovelDTO> getListAnexosImovelDTO() {
		return listAnexosImovelDTO;
	}

	public void setListAnexosImovelDTO(List<AnexosImovelDTO> listAnexosImovelDTO) {
		this.listAnexosImovelDTO = listAnexosImovelDTO;
	}

	public Long getTotalRegistros() {
		return totalRegistros;
	}

	public void setTotalRegistros(Long totalRegistros) {
		this.totalRegistros = totalRegistros;
	}

}
