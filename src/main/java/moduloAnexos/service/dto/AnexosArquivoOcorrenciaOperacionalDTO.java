package moduloAnexos.service.dto;

import moduloAnexos.util.ConvertObjectToJsonCesan;

public class AnexosArquivoOcorrenciaOperacionalDTO {

	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String toJson() {

		return ConvertObjectToJsonCesan.execute(this);
	}

	

}
