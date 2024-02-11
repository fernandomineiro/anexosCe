package moduloAnexos.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import moduloAnexos.autenticacao.JwtTokenProvider;
import moduloAnexos.comparator.AnexosSsGenericoDTOComparator;
import moduloAnexos.excecoes.ExcecaoRegraNegocio;
import moduloAnexos.excecoes.MsgGenericaPersonalizadaException;
import moduloAnexos.model.AnexosDadosSs;
import moduloAnexos.model.IdRegraUsuarioAnexos;
import moduloAnexos.model.RegraUsuarioAnexos;
import moduloAnexos.repository.AnexosDadosSsRepository;
import moduloAnexos.repository.RegraUsuarioAnexosRepository;
import moduloAnexos.service.AnexosBaixarSolicitacaoServicoService;
import moduloAnexos.service.AnexosDadosSsService;
import moduloAnexos.service.AnexosMedicaoSsService;
import moduloAnexos.service.AnexosSolicitacaoServicoService;
import moduloAnexos.service.AuditoriaService;
import moduloAnexos.service.dto.AnexosBaixarSolicitacaoServicoDTO;
import moduloAnexos.service.dto.AnexosDadosSsDTO;
import moduloAnexos.service.dto.AnexosMedicaoSsDTO;
import moduloAnexos.service.dto.AnexosSolicitacaoServicoDTO;
import moduloAnexos.service.dto.AnexosSsGenericoDTO;
import moduloAnexos.service.dto.AnexosSsGenericoWrapperDTO;
import moduloAnexos.service.dto.DownloadArquivoDTO;
import moduloAnexos.service.dto.SolicitacaoServicoDTO;
import moduloAnexos.service.mapper.AnexoSsGenericoMapper;
import moduloAnexos.service.mapper.AnexosSsBaixaGenericoMapper;
import moduloAnexos.service.mapper.AnexosSsDadosGenericoMapper;
import moduloAnexos.service.mapper.AnexosSsDadosMapper;
import moduloAnexos.service.mapper.AnexosSsMedicaoGenericoMapper;
import moduloAnexos.util.Constants;
import moduloAnexos.util.UrlMicroservico;
import moduloAnexos.util.ValidarArquivo;

@Service
@Transactional
public class AnexosDadosSsServiceImpl  implements AnexosDadosSsService{

	@Autowired
	private AnexosDadosSsRepository anexosDadosSsRepository;
	@Autowired
	private RegraUsuarioAnexosRepository regraUsuarioAnexosRepository;
	@Autowired
	private AnexosSolicitacaoServicoService anexosSolicitacaoServicoService;
	@Autowired
	private AnexosBaixarSolicitacaoServicoService anexosBaixarSolicitacaoServicoService;
	@Autowired
	private AnexosMedicaoSsService anexosMedicaoSsService;
	@Autowired
	private AuditoriaService auditoriaService;
	@Autowired
	private AnexoSsGenericoMapper anexoSsGenericoMapper;
	@Autowired
	private AnexosSsBaixaGenericoMapper anexosSsBaixaGenericoMapper;
	@Autowired
	private AnexosSsMedicaoGenericoMapper anexosSsMedicaoGenericoMapper;
	@Autowired
	private AnexosSsDadosGenericoMapper anexosSsDadosGenericoMapper;
	@Autowired
	private AnexosSsDadosMapper anexosSsDadosMapper;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private UrlMicroservico urlMicroservico;

	@Override
	public AnexosSsGenericoWrapperDTO buscarIdSs(Integer cdAtendimento, String refAtendimentoFormatada, Short seqSS,
			String token, Pageable pageable) {
		AnexosSsGenericoWrapperDTO anexosSsGenericoWrapperDTO = new AnexosSsGenericoWrapperDTO();
		Optional<RegraUsuarioAnexos> regraOp = regraUsuarioAnexosRepository
				.findById(new IdRegraUsuarioAnexos(64, jwtTokenProvider.buscarIdUsuario(token).intValue()));
		List<AnexosSsGenericoDTO> listAnexosSsGenericoDTO = new ArrayList<>();
		listAnexosSsGenericoDTO.addAll(this.buscarAnexosSs(cdAtendimento, refAtendimentoFormatada, seqSS));
		listAnexosSsGenericoDTO.addAll(this.buscarAnexosBaixaSs(cdAtendimento, refAtendimentoFormatada, seqSS));
		listAnexosSsGenericoDTO.addAll(this.buscarAnexoMedicaoSs(cdAtendimento, refAtendimentoFormatada, seqSS));
		listAnexosSsGenericoDTO
				.addAll(this.buscarAnexoMedicaoAntigaSs(cdAtendimento, refAtendimentoFormatada, seqSS, token));
		listAnexosSsGenericoDTO.addAll(this.buscarAnexosDadosSs(cdAtendimento, refAtendimentoFormatada, seqSS)); //this

		listAnexosSsGenericoDTO = listAnexosSsGenericoDTO.stream()
				.filter(e -> this.validarAcessoRestrito(e, token, regraOp)).collect(Collectors.toList());
		anexosSsGenericoWrapperDTO.setTotalRegistro(listAnexosSsGenericoDTO.size());
		anexosSsGenericoWrapperDTO.setTotalArquivo(listAnexosSsGenericoDTO.stream()
        	.map(e -> Optional.ofNullable(e.getTamanhoArquivo()).orElse(0))
        	.mapToInt(Integer::intValue)
        	.sum());
		String ordenarCampo = "";
		String ordem = "";
		if (pageable.getSort() != null && pageable.getSort().iterator().hasNext()) {
			Order order = pageable.getSort().iterator().next();
			ordenarCampo = order.getProperty();
			ordem = (order.isAscending() ? "asc" : "desc");
		}
		Collections.sort(listAnexosSsGenericoDTO, new AnexosSsGenericoDTOComparator(ordenarCampo, ordem));
		int indice = pageable.getPageSize() * pageable.getPageNumber();
		anexosSsGenericoWrapperDTO.setListAnexosSsGenericoDTO(listAnexosSsGenericoDTO.stream().skip(indice)
				.limit(pageable.getPageSize()).peek(e -> this.formatarRefAtendimento(e)).collect(Collectors.toList()));

		return anexosSsGenericoWrapperDTO;
	}
	
	public AnexosSsGenericoWrapperDTO buscarIdSs2(Integer cdAtendimento, String refAtendimentoFormatada, Short seqSS,
			String token, Pageable pageable) {
		AnexosSsGenericoWrapperDTO anexosSsGenericoWrapperDTO = new AnexosSsGenericoWrapperDTO();
		Optional<RegraUsuarioAnexos> regraOp = regraUsuarioAnexosRepository
				.findById(new IdRegraUsuarioAnexos(64, jwtTokenProvider.buscarIdUsuario(token).intValue()));
		List<AnexosSsGenericoDTO> listAnexosSsGenericoDTO = new ArrayList<>();
		listAnexosSsGenericoDTO.addAll(this.buscarAnexosSsAll(cdAtendimento, refAtendimentoFormatada, seqSS));
		listAnexosSsGenericoDTO.addAll(this.buscarAnexosBaixaSsAll(cdAtendimento, refAtendimentoFormatada, seqSS));
		listAnexosSsGenericoDTO.addAll(this.buscarAnexoMedicaoSsAll(cdAtendimento, refAtendimentoFormatada, seqSS));
		listAnexosSsGenericoDTO
				.addAll(this.buscarAnexoMedicaoAntigaSs(cdAtendimento, refAtendimentoFormatada, seqSS, token));
		listAnexosSsGenericoDTO.addAll(this.buscarAnexosDadosSsAll(cdAtendimento, refAtendimentoFormatada));

		listAnexosSsGenericoDTO = listAnexosSsGenericoDTO.stream()
				.filter(e -> this.validarAcessoRestrito(e, token, regraOp)).collect(Collectors.toList());
		anexosSsGenericoWrapperDTO.setTotalRegistro(listAnexosSsGenericoDTO.size());
		anexosSsGenericoWrapperDTO.setTotalArquivo(listAnexosSsGenericoDTO.stream().mapToInt(e -> e.getTamanhoArquivo()).sum());
		String ordenarCampo = "";
		String ordem = "";
		if (pageable.getSort() != null && pageable.getSort().iterator().hasNext()) {
			Order order = pageable.getSort().iterator().next();
			ordenarCampo = order.getProperty();
			ordem = (order.isAscending() ? "asc" : "desc");
		}
		Collections.sort(listAnexosSsGenericoDTO, new AnexosSsGenericoDTOComparator(ordenarCampo, ordem));
		int indice = pageable.getPageSize() * pageable.getPageNumber();
		anexosSsGenericoWrapperDTO.setListAnexosSsGenericoDTO(listAnexosSsGenericoDTO.stream().skip(indice)
				.limit(pageable.getPageSize()).peek(e -> this.formatarRefAtendimento(e)).collect(Collectors.toList()));

		return anexosSsGenericoWrapperDTO;
	}
	
	@Override
	public DownloadArquivoDTO buscarArquivoPorId(Long id, String origemArquivo) {
		return this.buscarArquivo(id, origemArquivo);
	}
	
	@Override
	public void salvar(AnexosDadosSsDTO anexosDadosSsDTO , String token) {
		SolicitacaoServicoDTO ssDTO= this.buscarSS(anexosDadosSsDTO);
		if(ssDTO.isFlagExclusao())
			 throw new MsgGenericaPersonalizadaException("Essa SS foi excluída.Não pode ser incluído um anexo");
		
		AnexosDadosSs anexosDadosSs = new AnexosDadosSs();
		byte[] arquivo= Base64.decodeBase64(anexosDadosSsDTO.getData());
		 

		anexosDadosSsDTO.setRefAtendimento("20" + anexosDadosSsDTO.getRefAtendimento().substring(3, 5) + anexosDadosSsDTO.getRefAtendimento().substring(0, 2));
		Integer tamanhoArquivos = anexosDadosSsRepository
				.buscarPorSSResumido(anexosDadosSsDTO.getCodAtendimento(), Integer.parseInt(anexosDadosSsDTO.getRefAtendimento()),
						anexosDadosSsDTO.getSeqSS())
				.stream().mapToInt(e -> e.getTamanhoArquivo()).sum() + arquivo.length;
		
		    ValidarArquivo.validarTotalArquivo(tamanhoArquivos);
		    ValidarArquivo.validarExtensaoArquivo(anexosDadosSsDTO.getNomeArquivo(), arquivo);
		    ValidarArquivo.validarTamanhoArquivo(arquivo.length);
			
		
		    anexosDadosSs.setArquivo(arquivo);
		    anexosDadosSs.setNomeArquivo(anexosDadosSsDTO.getNomeArquivo());
		    anexosDadosSs.setTamanhoArquivo(arquivo.length);		
		    anexosDadosSs.setCodAtendimento(anexosDadosSsDTO.getCodAtendimento());
		    anexosDadosSs.setRefAtendimento(Integer.parseInt(anexosDadosSsDTO.getRefAtendimento()));
		    anexosDadosSs.setSeqSS(anexosDadosSsDTO.getSeqSS());
		    anexosDadosSs.setDescricao(anexosDadosSsDTO.getDescricao());
		    anexosDadosSs.setUsuario(anexosDadosSsDTO.getUsuario());
		    anexosDadosSs.setAcessoRestrito(anexosDadosSsDTO.getAcessoRestrito());
			anexosDadosSsRepository.save(anexosDadosSs);

		String anexosDadosSsJson = anexosSsDadosMapper.toDto(anexosDadosSs).toJson();

		auditoriaService.gerarAuditoria(ssDTO.getId(), Constants.EMPTY_STRING, anexosDadosSsJson, 30L,
				"Dados SS", jwtTokenProvider.buscarIdUsuario(token)); 

		
	}
	@Override
	public void atualizar(AnexosDadosSsDTO anexosDadosSsDTO, String token) {
		SolicitacaoServicoDTO ssDTO= this.buscarSS(anexosDadosSsDTO);
		if(ssDTO.isFlagExclusao())
			 throw new MsgGenericaPersonalizadaException("Essa SS foi excluída.Não pode ser alterado o anexo");
		
		byte[] arquivo= Base64.decodeBase64(anexosDadosSsDTO.getData());
		
		anexosDadosSsDTO.setRefAtendimento("20" + anexosDadosSsDTO.getRefAtendimento().substring(3, 5) + anexosDadosSsDTO.getRefAtendimento().substring(0, 2));
		Integer tamanhoArquivos = arquivo.length;
		ValidarArquivo.validarTotalArquivo(tamanhoArquivos);
		ValidarArquivo.validarExtensaoArquivo(anexosDadosSsDTO.getNomeArquivo(), arquivo);
		ValidarArquivo.validarTamanhoArquivo(arquivo.length);
		
		AnexosDadosSs anexosDadosSs= anexosDadosSsRepository.findById(anexosDadosSsDTO.getId()).get();
		String anexosDadosSsJsonAntes = anexosSsDadosMapper.toDto(anexosDadosSs).toJson();
		
		anexosDadosSs.setArquivo(arquivo);
		anexosDadosSs.setNomeArquivo(anexosDadosSsDTO.getNomeArquivo());
		anexosDadosSs.setTamanhoArquivo(arquivo.length);		
		anexosDadosSs.setCodAtendimento(anexosDadosSsDTO.getCodAtendimento());
		anexosDadosSs.setRefAtendimento(Integer.parseInt(anexosDadosSsDTO.getRefAtendimento()));
		anexosDadosSs.setSeqSS(anexosDadosSsDTO.getSeqSS());
		anexosDadosSs.setDescricao(anexosDadosSsDTO.getDescricao());
		anexosDadosSs.setUsuario(anexosDadosSsDTO.getUsuario());
		anexosDadosSs.setAcessoRestrito(anexosDadosSsDTO.getAcessoRestrito());
		
		String anexosDadosSsJsonDepois = anexosSsDadosMapper.toDto(anexosDadosSs).toJson();
		anexosDadosSsRepository.save(anexosDadosSs);
		
		
		auditoriaService.gerarAuditoria(ssDTO.getId(), anexosDadosSsJsonAntes, anexosDadosSsJsonDepois, 30L,
				"Medição SS", jwtTokenProvider.buscarIdUsuario(token)); 
			
		
	}
	
	@Override
	public void excluir(Long id, String token) {
		String anexoDadosSsJson="";
		Long idSs=null;
		
		Optional<RegraUsuarioAnexos> regraOp=regraUsuarioAnexosRepository.findById(new IdRegraUsuarioAnexos(63, jwtTokenProvider.buscarIdUsuario(token).intValue()));
		
		if(regraOp.isEmpty())
			throw new ExcecaoRegraNegocio("Você não tem permissão de exclusão de anexo");
		
		
		      Optional<AnexosDadosSs> anexosDadosSsOp= anexosDadosSsRepository.findById(id);
		      if(anexosDadosSsOp.isEmpty())
		    	  throw new MsgGenericaPersonalizadaException("Este anexo não existe");
		      
		      
		      AnexosDadosSs anexosDadosSs =anexosDadosSsOp.get();
		      AnexosDadosSsDTO anexosDadosSsDTO=anexosSsDadosMapper.toDto(anexosDadosSs);	  
		      anexoDadosSsJson= anexosDadosSsDTO.toJson();
		      anexosDadosSsDTO.setRefAtendimento(this.formatarRefAtendimento(anexosDadosSsDTO.getRefAtendimento()));
	          idSs= this.buscarSS(anexosDadosSsDTO).getId();
	          anexosDadosSsRepository.delete(anexosDadosSs);
			  
		
		
		auditoriaService.gerarAuditoria(idSs, anexoDadosSsJson,
				Constants.EMPTY_STRING, 30L, "Dados SS", jwtTokenProvider.buscarIdUsuario(token));
		
	}
	
	
	
	private String formatarRefAtendimento(String refAtendimento) {
		return refAtendimento.substring(4, 6)+"/"+refAtendimento.substring(2, 4);
	}


	
	private List<AnexosSsGenericoDTO> buscarAnexosSs(Integer cdAtendimento, String refAtendimentoFormatada, Short seqSS){
		List<AnexosSolicitacaoServicoDTO>  listAnexoSsDTO= anexosSolicitacaoServicoService.buscarPorId(cdAtendimento, refAtendimentoFormatada, seqSS);
		List<AnexosSsGenericoDTO> listAnexosSsGenericoDTO= anexoSsGenericoMapper.toDto(listAnexoSsDTO);
		listAnexosSsGenericoDTO.forEach(e->e.setOrigemArquivo("Registrar"));
		return listAnexosSsGenericoDTO;
	}
	
	private List<AnexosSsGenericoDTO> buscarAnexosSsAll(Integer cdAtendimento, String refAtendimentoFormatada, Short seqSS){
		List<AnexosSolicitacaoServicoDTO>  listAnexoSsDTO= anexosSolicitacaoServicoService.buscarPorIdAll(cdAtendimento, refAtendimentoFormatada, seqSS);
		List<AnexosSsGenericoDTO> listAnexosSsGenericoDTO= anexoSsGenericoMapper.toDto(listAnexoSsDTO);
		listAnexosSsGenericoDTO.forEach(e->e.setOrigemArquivo("Registrar"));
		return listAnexosSsGenericoDTO;
	}
	
	private List<AnexosSsGenericoDTO> buscarAnexosBaixaSs(Integer cdAtendimento, String refAtendimentoFormatada, Short seqSS){
		List<AnexosBaixarSolicitacaoServicoDTO>  listAnexoBaixaSsDTO= anexosBaixarSolicitacaoServicoService.buscarPorId(cdAtendimento, refAtendimentoFormatada, seqSS);
		List<AnexosSsGenericoDTO> listAnexosSsGenericoDTO= anexosSsBaixaGenericoMapper.toDto(listAnexoBaixaSsDTO);
		listAnexosSsGenericoDTO.forEach(e->e.setOrigemArquivo("Baixar"));
		return listAnexosSsGenericoDTO;
	}
	
	private List<AnexosSsGenericoDTO> buscarAnexosBaixaSsAll(Integer cdAtendimento, String refAtendimentoFormatada, Short seqSS){
		List<AnexosBaixarSolicitacaoServicoDTO>  listAnexoBaixaSsDTO= anexosBaixarSolicitacaoServicoService.buscarPorIdAll(cdAtendimento, refAtendimentoFormatada, seqSS);
		List<AnexosSsGenericoDTO> listAnexosSsGenericoDTO= anexosSsBaixaGenericoMapper.toDto(listAnexoBaixaSsDTO);
		listAnexosSsGenericoDTO.forEach(e->e.setOrigemArquivo("Baixar"));
		return listAnexosSsGenericoDTO;
	}
	
	private List<AnexosSsGenericoDTO> buscarAnexoMedicaoSs(Integer cdAtendimento, String refAtendimentoFormatada, Short seqSS){
		 List<AnexosMedicaoSsDTO> listAnexosMedicaoSsDTO=anexosMedicaoSsService.buscarPorIdSs(cdAtendimento, refAtendimentoFormatada, seqSS);
		 List<AnexosSsGenericoDTO> listAnexosSsGenericoDTO =anexosSsMedicaoGenericoMapper.toDto(listAnexosMedicaoSsDTO);
		 listAnexosSsGenericoDTO.forEach(e->e.setOrigemArquivo("Medição"));
		 return listAnexosSsGenericoDTO;
	}
	
	private List<AnexosSsGenericoDTO> buscarAnexoMedicaoSsAll(Integer cdAtendimento, String refAtendimentoFormatada, Short seqSS){
		 List<AnexosMedicaoSsDTO> listAnexosMedicaoSsDTO=anexosMedicaoSsService.buscarPorIdSsAll(cdAtendimento, refAtendimentoFormatada, seqSS);
		 List<AnexosSsGenericoDTO> listAnexosSsGenericoDTO =anexosSsMedicaoGenericoMapper.toDto(listAnexosMedicaoSsDTO);
		 listAnexosSsGenericoDTO.forEach(e->e.setOrigemArquivo("Medição"));
		 return listAnexosSsGenericoDTO;
	}
	
	private List<AnexosSsGenericoDTO> buscarAnexoMedicaoAntigaSs(Integer cdAtendimento, String refAtendimentoFormatada, Short seqSS,String token){
		 List<AnexosMedicaoSsDTO> listAnexosMedicaoSsDTO=anexosMedicaoSsService.buscarAnexoAntigoPorIdSs(cdAtendimento, refAtendimentoFormatada, seqSS);
		 List<AnexosSsGenericoDTO> listAnexosSsGenericoDTO =anexosSsMedicaoGenericoMapper.toDto(listAnexosMedicaoSsDTO);
		 listAnexosSsGenericoDTO.forEach(e->e.setOrigemArquivo("Medição Antiga"));
		 return listAnexosSsGenericoDTO;
	}
	
	private List<AnexosSsGenericoDTO> buscarAnexosDadosSs(Integer cdAtendimento, String refAtendimentoFormatada, Short seqSS){
		Integer refAtendimento = Integer
				.parseInt("20" + refAtendimentoFormatada.substring(3, 5) + refAtendimentoFormatada.substring(0, 2));
		 List<AnexosDadosSsDTO> listAnexosDadosSsDTO=anexosDadosSsRepository.buscarPorSS(cdAtendimento, refAtendimento, seqSS);
		 List<AnexosSsGenericoDTO> listAnexosSsGenericoDTO = anexosSsDadosGenericoMapper.toDto(listAnexosDadosSsDTO);
		 listAnexosSsGenericoDTO.forEach(e->e.setOrigemArquivo("Dados"));
		 return listAnexosSsGenericoDTO;
	}
	
	private List<AnexosSsGenericoDTO> buscarAnexosDadosSsAll(Integer cdAtendimento, String refAtendimentoFormatada){
		Integer refAtendimento = Integer
				.parseInt("20" + refAtendimentoFormatada.substring(3, 5) + refAtendimentoFormatada.substring(0, 2));
		 List<AnexosDadosSsDTO> listAnexosDadosSsDTO=anexosDadosSsRepository.buscarPorSSAll(cdAtendimento, refAtendimento);
		 List<AnexosSsGenericoDTO> listAnexosSsGenericoDTO = anexosSsDadosGenericoMapper.toDto(listAnexosDadosSsDTO);
		 listAnexosSsGenericoDTO.forEach(e->e.setOrigemArquivo("Dados"));
		 return listAnexosSsGenericoDTO;
	}
	
	private DownloadArquivoDTO buscarArquivo(Long id, String origemArquivo) {
		DownloadArquivoDTO downloadArquivoDTO=null;
		switch(origemArquivo) {

        case "Registrar":
        	downloadArquivoDTO = anexosSolicitacaoServicoService.buscarArquivoPorId(id);
            break;

        case "Baixar":
        	downloadArquivoDTO = anexosBaixarSolicitacaoServicoService.buscarArquivoPorId(id);
            break;

        case "Medição":
        	downloadArquivoDTO = anexosMedicaoSsService.buscarArquivoPorId(id, false);
            break;
        case "Medição Antiga":
        	downloadArquivoDTO = anexosMedicaoSsService.buscarArquivoPorId(id, true);
            break;  
        case "Dados":
        	downloadArquivoDTO = this.buscarArquivoPorId(id);
            break;      
    }
		return downloadArquivoDTO;
	}
	
	
	private DownloadArquivoDTO buscarArquivoPorId(Long id) {
		Optional<AnexosDadosSs> anexosDadosSs = anexosDadosSsRepository.findById(id);
		DownloadArquivoDTO downloadArquivoDTO = new DownloadArquivoDTO();
		

		if (anexosDadosSs.isEmpty()) {

			throw new MsgGenericaPersonalizadaException("Anexo de SS  não existe ou foi excluído");
		}
		downloadArquivoDTO.setData(Base64.encodeBase64String(anexosDadosSs.get().getArquivo()));
		downloadArquivoDTO.setNomeArquivo(anexosDadosSs.get().getNomeArquivo());
		return downloadArquivoDTO;
	}
	
	private SolicitacaoServicoDTO buscarSS(AnexosDadosSsDTO anexosDadosSsDTO) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		RestTemplate restTemplate = new RestTemplate();
		String url = urlMicroservico.getUrlServico() + "/backend-servico/solicitacaoServico";
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("refAtendimento", anexosDadosSsDTO.getRefAtendimento())
				.queryParam("codAtendimento", anexosDadosSsDTO.getCodAtendimento()).queryParam("ssSequencial", anexosDadosSsDTO.getSeqSS());
		HttpEntity<?> request = new HttpEntity<>(headers);
		 try {
		HttpEntity<SolicitacaoServicoDTO> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, request,
				SolicitacaoServicoDTO.class);
		
		
		return response.getBody();
		 }catch ( HttpClientErrorException httpClientErrorException) {
		        throw new MsgGenericaPersonalizadaException("SS não existe");
		  }
		
		
	}
	
	private void formatarRefAtendimento(AnexosSsGenericoDTO anexosSsGenericoDTO) {
		String refAtendimento;
		if(anexosSsGenericoDTO.getRefAtendimento() != null){
			refAtendimento = anexosSsGenericoDTO.getRefAtendimento().substring(4, 6) + "/"
			+ anexosSsGenericoDTO.getRefAtendimento().substring(2, 4);
		}else{
			refAtendimento = "01/01";
		}
		anexosSsGenericoDTO.setRefAtendimento(refAtendimento);
	}

	private boolean validarAcessoRestrito(AnexosSsGenericoDTO anexosSsGenericoDTO, String token,
			Optional<RegraUsuarioAnexos> regraOp) {
		if (regraOp.isEmpty() && anexosSsGenericoDTO.getAcessoRestrito() != null
				&& anexosSsGenericoDTO.getAcessoRestrito().equalsIgnoreCase("S"))
			return false;
		else
			return true;

	}
	
	

	

}
