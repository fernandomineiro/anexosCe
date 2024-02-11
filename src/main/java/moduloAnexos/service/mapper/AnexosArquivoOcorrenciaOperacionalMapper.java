package moduloAnexos.service.mapper;

import org.mapstruct.Mapper;

import moduloAnexos.model.AnexosOcorrenciaOperacional;
import moduloAnexos.service.dto.AnexosArquivoOcorrenciaOperacionalDTO;

@Mapper(componentModel = "spring")
public interface AnexosArquivoOcorrenciaOperacionalMapper extends EntityMapper<AnexosArquivoOcorrenciaOperacionalDTO, AnexosOcorrenciaOperacional> {

}
