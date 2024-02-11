package moduloAnexos.service.mapper;

import org.mapstruct.Mapper;

import moduloAnexos.model.AnexosBeneficiario;
import moduloAnexos.service.dto.AnexosBeneficiarioDTO;

@Mapper(componentModel = "spring")
public interface AnexosBeneficiarioMapper extends EntityMapper<AnexosBeneficiarioDTO, AnexosBeneficiario> {

}
