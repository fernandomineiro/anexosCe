package moduloAnexos.service.dto;

import java.util.List;

public class AnexosOcorrenciaOperacionalWrapperDTO {

	private List<AnexosOcorrenciaOperacionalDTO> listAnexosOcorrenciaOperacionalDTO;

	private Long totalRegistros;
	
	private Boolean registroExcluido = Boolean.FALSE;
	
	public List<AnexosOcorrenciaOperacionalDTO> getListAnexosOcorrenciaOperacionalDTO() {
		return listAnexosOcorrenciaOperacionalDTO;
	}

	public void setListAnexosOcorrenciaOperacionalDTO(
			List<AnexosOcorrenciaOperacionalDTO> listAnexosOcorrenciaOperacionalDTO) {
		this.listAnexosOcorrenciaOperacionalDTO = listAnexosOcorrenciaOperacionalDTO;
	}

	public Long getTotalRegistros() {
		return totalRegistros;
	}

	public void setTotalRegistros(Long totalRegistros) {
		this.totalRegistros = totalRegistros;
	}

	public Boolean getRegistroExcluido() {
		return registroExcluido;
	}

	public void setRegistroExcluido(Boolean registroExcluido) {
		this.registroExcluido = registroExcluido;
	}

}
