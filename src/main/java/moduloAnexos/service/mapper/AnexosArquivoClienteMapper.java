package moduloAnexos.service.mapper;

import org.mapstruct.Mapper;

import moduloAnexos.model.AnexosCliente;
import moduloAnexos.service.dto.AnexosArquivoClienteDTO;

@Mapper(componentModel = "spring")
public interface AnexosArquivoClienteMapper extends EntityMapper<AnexosArquivoClienteDTO, AnexosCliente> {

}
