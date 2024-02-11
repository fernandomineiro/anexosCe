package moduloAnexos.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="CAD_CLIENTE_MOTIVO_ESPECIAL")
public class ClienteMotivoEspecial extends EntityBase {

	@JoinColumn(name="ID_MOTIVO_ESPECIAL")
	@ManyToOne(fetch = FetchType.EAGER)
	private TipoMotivoClienteEspecial tipoMotivoClienteEspecial;
	
	private Integer cdCliente;

	public TipoMotivoClienteEspecial getTipoMotivoClienteEspecial() {
		return tipoMotivoClienteEspecial;
	}

	public void setTipoMotivoClienteEspecial(TipoMotivoClienteEspecial tipoMotivoClienteEspecial) {
		this.tipoMotivoClienteEspecial = tipoMotivoClienteEspecial;
	}

	public Integer getCdCliente() {
		return cdCliente;
	}

	public void setCdCliente(Integer cdCliente) {
		this.cdCliente = cdCliente;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((tipoMotivoClienteEspecial == null) ? 0 : tipoMotivoClienteEspecial.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClienteMotivoEspecial other = (ClienteMotivoEspecial) obj;
		if (tipoMotivoClienteEspecial == null) {
			if (other.tipoMotivoClienteEspecial != null)
				return false;
		} else if (!tipoMotivoClienteEspecial.equals(other.tipoMotivoClienteEspecial))
			return false;
		return true;
	}

}
