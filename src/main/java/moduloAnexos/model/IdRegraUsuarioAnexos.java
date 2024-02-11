package moduloAnexos.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class IdRegraUsuarioAnexos implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "idregra")
	private Integer idregra;
	@Column(name = "ID_USUARIO")
	private Integer idUsuario;
	
	public IdRegraUsuarioAnexos() {
		super();
	}
	
	public IdRegraUsuarioAnexos(Integer idRegra, Integer idUsuario) {
		super();
		this.idregra = idRegra;
		this.idUsuario = idUsuario;
	}
	
	public Integer getIdregra() {
		return idregra;
	}
	public void setIdregra(Integer idregra) {
		this.idregra = idregra;
	}
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	
}
