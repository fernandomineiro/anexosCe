package moduloAnexos.controller.agencia;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import moduloAnexos.service.AnexosAgenciaSolicitacaoServicoService;
import moduloAnexos.service.AnexosAgenciaVazamentoSSService;
import moduloAnexos.service.dto.AnexosSolicitacaoServicoDTO;
import moduloAnexos.service.dto.AnexosSolicitacaoServicoWrapperDTO;
import moduloAnexos.service.dto.DownloadArquivoDTO;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping("/backend-anexos/agenciaVirtual/vazamentoSolicitacaoServico")
public class AnexosVazamentoSolicitacaoServicoController {

    @Autowired
    private AnexosAgenciaSolicitacaoServicoService serviceAgenciaSolicitacaoServico;
    @Autowired
    private AnexosAgenciaVazamentoSSService serviceVazamentoSolicitacaoServico;

    @CrossOrigin
    @GetMapping
    public AnexosSolicitacaoServicoWrapperDTO buscarPorID(@RequestParam(value = "refAtendimento", required = true) String refAtendimento, @RequestParam(value = "codAtendimento", required = true) Integer codAtendimento, @RequestParam(value = "ssSequencial", required = true) Short ssSequencial , Pageable pageable) {
        return this.serviceAgenciaSolicitacaoServico.buscarPorId(codAtendimento, refAtendimento, ssSequencial, pageable);
    }

    @CrossOrigin
    @PostMapping
    public AnexosSolicitacaoServicoDTO cadastrar(@RequestBody AnexosSolicitacaoServicoDTO anexosSolicitacaoServicoDTO,@RequestHeader("Authorization") String token) {
        return this.serviceVazamentoSolicitacaoServico.salvar(anexosSolicitacaoServicoDTO, token);
    }

    @CrossOrigin
    @GetMapping("/downloadArquivo/{id}")
    public DownloadArquivoDTO downloadArquivo(@PathVariable Long id, HttpServletResponse response) {
        return this.serviceAgenciaSolicitacaoServico.buscarArquivoPorId(id);
    }
}
