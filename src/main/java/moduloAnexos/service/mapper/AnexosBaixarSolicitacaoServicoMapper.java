package moduloAnexos.service.mapper;

import org.mapstruct.Mapper;

import moduloAnexos.model.AnexosBaixarSolicitacaoServico;
import moduloAnexos.service.dto.AnexosBaixarSolicitacaoServicoDTO;

@Mapper(componentModel="spring")
public interface AnexosBaixarSolicitacaoServicoMapper extends EntityMapper<AnexosBaixarSolicitacaoServicoDTO, AnexosBaixarSolicitacaoServico> {

}
