package moduloAnexos.service.mapper;

import org.mapstruct.Mapper;

import moduloAnexos.model.AnexosFiscalizacao;
import moduloAnexos.service.dto.AnexosFiscalizacaoDTO;

@Mapper(componentModel = "spring")
public interface AnexosFiscalizacaoMapper extends EntityMapper<AnexosFiscalizacaoDTO, AnexosFiscalizacao>{

}