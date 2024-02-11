package moduloAnexos.service.mapper;

import org.mapstruct.Mapper;

import moduloAnexos.model.AnexosRetornoIrregularidade;
import moduloAnexos.service.dto.AnexosRetornoIrregularidadeDTO;

@Mapper(componentModel = "spring")
public interface AnexosRetornoIrregularidadeMapper extends EntityMapper<AnexosRetornoIrregularidadeDTO, AnexosRetornoIrregularidade> {

}
