package moduloAnexos.rabbitmq.produtores.mensagens;

public class AreaAtuacaoMsgProdutor {

	private Long id;
	private String nome ;
	private String statusReg;
	
	public AreaAtuacaoMsgProdutor(Long id, String nome, String statusReg) {
		super();
		this.id = id;
		this.nome = nome;
		this.statusReg = statusReg;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getStatusReg() {
		return statusReg;
	}
	public void setStatusReg(String statusReg) {
		this.statusReg = statusReg;
	}
	
	
}
