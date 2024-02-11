package moduloAnexos.service.mapper;

import org.mapstruct.Mapper;

import moduloAnexos.service.dto.AnexosBaixarSolicitacaoServicoDTO;
import moduloAnexos.service.dto.AnexosSsGenericoDTO;

@Mapper(componentModel = "spring")
public interface AnexosSsBaixaGenericoMapper extends EntityMapper<AnexosSsGenericoDTO, AnexosBaixarSolicitacaoServicoDTO> {

}
