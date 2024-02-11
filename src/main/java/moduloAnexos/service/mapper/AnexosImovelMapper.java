package moduloAnexos.service.mapper;

import org.mapstruct.Mapper;

import moduloAnexos.model.AnexosImovel;
import moduloAnexos.service.dto.AnexosImovelDTO;

@Mapper(componentModel = "spring")
public interface AnexosImovelMapper extends EntityMapper<AnexosImovelDTO, AnexosImovel> {

}
