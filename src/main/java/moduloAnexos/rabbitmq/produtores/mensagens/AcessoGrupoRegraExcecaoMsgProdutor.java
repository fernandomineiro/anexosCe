package moduloAnexos.rabbitmq.produtores.mensagens;

import java.util.List;

public class AcessoGrupoRegraExcecaoMsgProdutor {
	private Long idRegra;
	private List<Long> usuarios;
	private String acao;
	
	public Long getIdRegra() {
		return idRegra;
	}
	public void setIdRegra(Long idRegra) {
		this.idRegra = idRegra;
	}
	public List<Long> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(List<Long> usuarios) {
		this.usuarios = usuarios;
	}
	public String getAcao() {
		return acao;
	}
	public void setAcao(String acao) {
		this.acao = acao;
	}
	
}
