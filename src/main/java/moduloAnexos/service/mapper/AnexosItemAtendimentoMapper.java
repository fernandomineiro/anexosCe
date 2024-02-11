package moduloAnexos.service.mapper;

import org.mapstruct.Mapper;

import moduloAnexos.model.AnexosItemAtendimento;
import moduloAnexos.service.dto.AnexosItemAtendimentoDTO;

@Mapper(componentModel = "spring")
public interface AnexosItemAtendimentoMapper extends EntityMapper<AnexosItemAtendimentoDTO, AnexosItemAtendimento> {

}
