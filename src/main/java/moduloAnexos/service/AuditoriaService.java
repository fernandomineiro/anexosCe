package moduloAnexos.service;

public interface AuditoriaService {

	
	public void gerarAuditoria(Long chave,String dadosAntes, String dadosDepois, Long idEntidade, String rotina,
			Long idUsuario);
}
