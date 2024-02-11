package moduloAnexos.service.mapper;

import org.mapstruct.Mapper;

import moduloAnexos.model.AnexosCliente;
import moduloAnexos.service.dto.AnexosClienteDTO;

@Mapper(componentModel = "spring")
public interface AnexosClienteMapper extends EntityMapper<AnexosClienteDTO, AnexosCliente> {

}
