package moduloAnexos.service.mapper;

import org.mapstruct.Mapper;

import moduloAnexos.service.dto.AnexosDadosSsDTO;
import moduloAnexos.service.dto.AnexosSsGenericoDTO;

@Mapper(componentModel = "spring")
public interface AnexosSsDadosGenericoMapper extends EntityMapper<AnexosSsGenericoDTO, AnexosDadosSsDTO>  {

}
