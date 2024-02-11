package moduloAnexos.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import moduloAnexos.service.AnexosBaixarSolicitacaoServicoService;
import moduloAnexos.service.AnexosSolicitacaoServicoService;
import moduloAnexos.service.dto.AnexosSolicitacaoServicoDTO;
import moduloAnexos.service.dto.AnexosSolicitacaoServicoWrapperDTO;
import moduloAnexos.service.dto.DownloadArquivoDTO;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping("/backend-anexosBaixarPesquisaIrregularidade/anexos")
public class AnexosBaixarPesquisaIrregularidadeController {
	
	@Autowired
	private AnexosSolicitacaoServicoService anexosSolicitacaoServicoService;
		
	@CrossOrigin
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public AnexosSolicitacaoServicoDTO cadastrar(@RequestBody AnexosSolicitacaoServicoDTO anexosSolicitacaoServicoDTO,
			@RequestHeader("Authorization") String token) {
		return anexosSolicitacaoServicoService.salvar(anexosSolicitacaoServicoDTO, token);
	}
	
	@CrossOrigin
	@DeleteMapping("/{id}")
	public void excluir(@PathVariable(value = "id") Long id, @RequestHeader("Authorization") String token) {
		anexosSolicitacaoServicoService.excluir(id, token);
	}

	@CrossOrigin
	@GetMapping
	public AnexosSolicitacaoServicoWrapperDTO buscarPorID(
			@RequestParam(value = "refAtendimento", required = true) Integer refAtendimento,
			@RequestParam(value = "codAtendimento", required = true) Integer codAtendimento,
			@RequestParam(value = "ssSequencial", required = true) Short ssSequencial ,Pageable pageable) {
		return anexosSolicitacaoServicoService.buscarPorId(codAtendimento, refAtendimento, ssSequencial, pageable);
	}

	
	
	@CrossOrigin
	@GetMapping("/downloadArquivo/{id}")
    public DownloadArquivoDTO downloadArquivo(@PathVariable Long id,HttpServletResponse response) {
		return anexosSolicitacaoServicoService.buscarArquivoPorId(id);
        
    }	
}
