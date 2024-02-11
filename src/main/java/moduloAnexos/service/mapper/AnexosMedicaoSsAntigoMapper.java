package moduloAnexos.service.mapper;

import org.mapstruct.Mapper;

import moduloAnexos.model.AnexosMedicaoSsAntigo;
import moduloAnexos.service.dto.AnexosMedicaoSsDTO;

@Mapper(componentModel = "spring")
public interface AnexosMedicaoSsAntigoMapper  extends EntityMapper<AnexosMedicaoSsDTO, AnexosMedicaoSsAntigo> {

}
