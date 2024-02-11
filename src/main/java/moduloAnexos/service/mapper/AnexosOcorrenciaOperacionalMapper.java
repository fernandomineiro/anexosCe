package moduloAnexos.service.mapper;

import org.mapstruct.Mapper;

import moduloAnexos.model.AnexosOcorrenciaOperacional;
import moduloAnexos.service.dto.AnexosOcorrenciaOperacionalDTO;

@Mapper(componentModel = "spring")
public interface AnexosOcorrenciaOperacionalMapper extends EntityMapper<AnexosOcorrenciaOperacionalDTO, AnexosOcorrenciaOperacional> {

}
