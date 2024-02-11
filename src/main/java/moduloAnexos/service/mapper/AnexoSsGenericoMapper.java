package moduloAnexos.service.mapper;

import org.mapstruct.Mapper;

import moduloAnexos.service.dto.AnexosSolicitacaoServicoDTO;
import moduloAnexos.service.dto.AnexosSsGenericoDTO;

@Mapper(componentModel = "spring")
public interface AnexoSsGenericoMapper extends EntityMapper<AnexosSsGenericoDTO, AnexosSolicitacaoServicoDTO>{

}
