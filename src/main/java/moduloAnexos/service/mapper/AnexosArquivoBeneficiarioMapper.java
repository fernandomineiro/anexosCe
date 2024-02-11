package moduloAnexos.service.mapper;

import org.mapstruct.Mapper;

import moduloAnexos.model.AnexosBeneficiario;
import moduloAnexos.service.dto.AnexosArquivoBeneficiarioDTO;

@Mapper(componentModel = "spring")
public interface AnexosArquivoBeneficiarioMapper extends EntityMapper<AnexosArquivoBeneficiarioDTO, AnexosBeneficiario> {

}
