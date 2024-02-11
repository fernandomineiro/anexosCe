package moduloAnexos.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CAD_ANEXOS_REGRA_USUARIO")
public class RegraUsuarioAnexos {

	@EmbeddedId
	@moduloAnexos.util.customAnnotation.JsonCesanNotSerializable
	private IdRegraUsuarioAnexos idRegraUsuarioAnexos;

	public RegraUsuarioAnexos() {
		super();
	}

	public RegraUsuarioAnexos(IdRegraUsuarioAnexos idRegraUsuarioAnexos) {
		super();
		this.idRegraUsuarioAnexos = idRegraUsuarioAnexos;
	}

	public IdRegraUsuarioAnexos getIdRegraUsuarioAnexos() {
		return idRegraUsuarioAnexos;
	}

	public void setIdRegraUsuarioAnexos(IdRegraUsuarioAnexos idRegraUsuarioAnexos) {
		this.idRegraUsuarioAnexos = idRegraUsuarioAnexos;
	}

}
