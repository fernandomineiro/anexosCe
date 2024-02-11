package moduloAnexos.service.dto;

import java.util.List;

public class AnexosOshDTO {
	
	private Integer matricula;
	private Integer refOsh;
	private Integer numOsh;
	private List<UploadArquivoDTO> listUploadArquivoDTO;

	
	
	public Integer getMatricula() {
		return matricula;
	}
	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}
	public Integer getRefOsh() {
		return refOsh;
	}
	public void setRefOsh(Integer refOsh) {
		this.refOsh = refOsh;
	}
	public Integer getNumOsh() {
		return numOsh;
	}
	public void setNumOsh(Integer numOsh) {
		this.numOsh = numOsh;
	}
	public List<UploadArquivoDTO> getListUploadArquivoDTO() {
		return listUploadArquivoDTO;
	}
	public void setListUploadArquivoDTO(List<UploadArquivoDTO> listUploadArquivoDTO) {
		this.listUploadArquivoDTO = listUploadArquivoDTO;
	}

	
	
}
