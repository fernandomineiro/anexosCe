package moduloAnexos.service.mapper;

import org.mapstruct.Mapper;

import moduloAnexos.model.AnexosImovel;
import moduloAnexos.service.dto.AnexosArquivoImovelDTO;

@Mapper(componentModel = "spring")
public interface AnexosArquivoImovelMapper extends EntityMapper<AnexosArquivoImovelDTO, AnexosImovel> {

}
