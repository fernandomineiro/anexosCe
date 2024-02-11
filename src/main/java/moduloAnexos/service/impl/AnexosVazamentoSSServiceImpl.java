package moduloAnexos.service.impl;

import moduloAnexos.model.AnexosSolicitacaoServico;
import moduloAnexos.service.AnexosAgenciaSolicitacaoServicoService;
import moduloAnexos.service.AnexosAgenciaVazamentoSSService;
import moduloAnexos.service.dto.AnexosSolicitacaoServicoDTO;
import moduloAnexos.service.validacoes.ValidarArquivoAgencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional
public class AnexosVazamentoSSServiceImpl implements AnexosAgenciaVazamentoSSService {

    @Autowired
    private AnexosAgenciaSolicitacaoServicoService anexosAgenciaSolicitacaoServicoService;

    @Override
    public AnexosSolicitacaoServicoDTO salvar(AnexosSolicitacaoServicoDTO anexosSolicitacaoServicoDTO, String token) {
        final int LIMITE_MEGA_ARQUIVO = 5;
        final int TAMANHO_MAXIMO_ARQUIVO = 5000;
        AnexosSolicitacaoServico anexosSolicitacaoServico = new AnexosSolicitacaoServico();
        List<String> extensoesInvalidas = ValidarArquivoAgencia.buscarExtensoesInvalidasFoto();
        return this.anexosAgenciaSolicitacaoServicoService.salvar(anexosSolicitacaoServicoDTO, token, anexosSolicitacaoServico, LIMITE_MEGA_ARQUIVO, TAMANHO_MAXIMO_ARQUIVO, extensoesInvalidas);
    }
}
