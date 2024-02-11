package moduloAnexos.service.mapper;

import org.mapstruct.Mapper;

import moduloAnexos.model.AnexosSolicitacaoServico;
import moduloAnexos.service.dto.AnexosSolicitacaoServicoDTO;

@Mapper(componentModel = "spring")
public interface AnexosSolicitacaoServicoMapper extends EntityMapper<AnexosSolicitacaoServicoDTO, AnexosSolicitacaoServico> {

}
