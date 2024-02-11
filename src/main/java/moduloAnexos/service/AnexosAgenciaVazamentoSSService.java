package moduloAnexos.service;

import moduloAnexos.service.dto.AnexosSolicitacaoServicoDTO;

public interface AnexosAgenciaVazamentoSSService {

    AnexosSolicitacaoServicoDTO salvar(AnexosSolicitacaoServicoDTO anexosSolicitacaoServicoDTO, String token);
}
