package moduloAnexos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CAD_TP_CLIENTE")
public class TipoCliente {

	@Id
	@Column(name = "ID_TP_CLIENTE")
	private Short id;
	
	@Column(name = "DC_DESCRICAO")
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Short getId() {
		return id;
	}

	public void setId(Short id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return this.descricao;
	}
	
}
