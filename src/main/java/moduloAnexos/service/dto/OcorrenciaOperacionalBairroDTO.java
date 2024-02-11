package moduloAnexos.service.dto;

public class OcorrenciaOperacionalBairroDTO {

	private Long id;
	
	private Long idOcorrenciaOperacional;
	
	private BairroDTO bairro;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdOcorrenciaOperacional() {
		return idOcorrenciaOperacional;
	}

	public void setIdOcorrenciaOperacional(Long idOcorrenciaOperacional) {
		this.idOcorrenciaOperacional = idOcorrenciaOperacional;
	}

	public BairroDTO getBairro() {
		return bairro;
	}

	public void setBairro(BairroDTO bairro) {
		this.bairro = bairro;
	}
	
}