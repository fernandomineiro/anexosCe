package moduloAnexos.service.mapper;

import org.mapstruct.Mapper;

import moduloAnexos.model.AnexosDadosSs;
import moduloAnexos.service.dto.AnexosDadosSsDTO;

@Mapper(componentModel = "spring")
public interface AnexosSsDadosMapper  extends EntityMapper<AnexosDadosSsDTO, AnexosDadosSs> {

}
