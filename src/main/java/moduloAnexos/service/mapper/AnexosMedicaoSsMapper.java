package moduloAnexos.service.mapper;

import org.mapstruct.Mapper;

import moduloAnexos.model.AnexosMedicaoSs;
import moduloAnexos.service.dto.AnexosMedicaoSsDTO;

@Mapper(componentModel = "spring")
public interface AnexosMedicaoSsMapper extends EntityMapper<AnexosMedicaoSsDTO, AnexosMedicaoSs>  {

}
