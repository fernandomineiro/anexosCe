package moduloAnexos.service.dto;

import java.util.List;

public class AnexosItemAtendimentoWrapperDTO {

	private List<AnexosItemAtendimentoDTO> listAnexosItemAtendimentoDTO;
	private Integer totalArquivo;
	private long totalRegistro;

	public List<AnexosItemAtendimentoDTO> getListAnexosItemAtendimentoDTO() {
		return listAnexosItemAtendimentoDTO;
	}

	public void setListAnexosItemAtendimentoDTO(List<AnexosItemAtendimentoDTO> listAnexosItemAtendimentoDTO) {
		this.listAnexosItemAtendimentoDTO = listAnexosItemAtendimentoDTO;
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
