package moduloAnexos.excecoes;

public enum MsgGenericaParametrizadaEnum {

	MENSAGEM_ERRO_VALIDACAO_SS("Erro genérico de validação de cadastro do siscon"),
	TIPO_ARQUIVO_INVALIDO("tipo do arquivo está inválido"),
	SS_NAO_ENCONTRADA("SS não encontrada"),
	TAMANHO_ARQUIVO_INVALIDO("tamanho invalido"),
	ARQUIVO_MUITO_GRANDE("tamanho invalido");


	private String message;

	MsgGenericaParametrizadaEnum(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public String getClientCode() {
		return this.toString().toLowerCase();
	}
}
