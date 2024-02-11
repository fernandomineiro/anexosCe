package moduloAnexos.service.mapper;

import org.mapstruct.Mapper;

import moduloAnexos.service.dto.AnexosMedicaoSsDTO;
import moduloAnexos.service.dto.AnexosSsGenericoDTO;

@Mapper(componentModel = "spring")
public interface AnexosSsMedicaoGenericoMapper extends EntityMapper<AnexosSsGenericoDTO, AnexosMedicaoSsDTO>{

}
