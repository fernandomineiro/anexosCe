package moduloAnexos.service.dto;

import java.util.List;

public class AnexosSsGenericoWrapperDTO {

	private List<AnexosSsGenericoDTO> listAnexosSsGenericoDTO;
	private long totalRegistro;
	private Integer totalArquivo;

	public List<AnexosSsGenericoDTO> getListAnexosSsGenericoDTO() {
		return listAnexosSsGenericoDTO;
	}
	public void setTotalArquivo(Integer totalArquivo) {
		this.totalArquivo = totalArquivo;
	}

	public Integer getTotalArquivo() {
		return totalArquivo;
	}
	
	public void setListAnexosSsGenericoDTO(List<AnexosSsGenericoDTO> listAnexosSsGenericoDTO) {
		this.listAnexosSsGenericoDTO = listAnexosSsGenericoDTO;
	}
	public long getTotalRegistro() {
		return totalRegistro;
	}
	public void setTotalRegistro(long totalRegistro) {
		this.totalRegistro = totalRegistro;
	}
	
	
	
}
