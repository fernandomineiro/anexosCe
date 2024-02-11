package moduloAnexos.service.dto;

import moduloAnexos.util.ConvertObjectToJsonCesan;

public class AnexosDownloadArquivoDTO {

	private Long id;

	private String nomeArquivo;

	
	private byte[] arquivo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public byte[] getArquivo() {
		return arquivo;
	}

	public void setArquivo(byte[] arquivo) {
		this.arquivo = arquivo;
	}

	public String toJson() {

		return ConvertObjectToJsonCesan.execute(this);
	}

}
