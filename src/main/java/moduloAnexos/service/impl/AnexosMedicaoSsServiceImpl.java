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
import moduloAnexos.comparator.AnexosMedicaoSsDTOComparator;
import moduloAnexos.excecoes.MsgGenericaPersonalizadaException;
import moduloAnexos.model.AnexosMedicaoSs;
import moduloAnexos.model.AnexosMedicaoSsAntigo;
import moduloAnexos.repository.AnexosMedicaoSsAntigoRepository;
import moduloAnexos.repository.AnexosMedicaoSsRepository;
import moduloAnexos.service.AnexosMedicaoSsService;
import moduloAnexos.service.AuditoriaService;
import moduloAnexos.service.dto.AnexosMedicaoSsDTO;
import moduloAnexos.service.dto.AnexosMedicaoSsWrapperDTO;
import moduloAnexos.service.dto.DownloadArquivoDTO;
import moduloAnexos.service.dto.MedicaoSolicitacaoServicoComplDTO;
import moduloAnexos.service.dto.SolicitacaoServicoDTO;
import moduloAnexos.service.mapper.AnexosMedicaoSsAntigoMapper;
import moduloAnexos.service.mapper.AnexosMedicaoSsMapper;
import moduloAnexos.util.Constants;
import moduloAnexos.util.UrlMicroservico;
import moduloAnexos.util.ValidarArquivo;

@Service
@Transactional
public class AnexosMedicaoSsServiceImpl implements AnexosMedicaoSsService {

	@Autowired
	private UrlMicroservico urlMicroservico;
	@Autowired
	private AnexosMedicaoSsRepository anexosMedicaoSsRepository;
	@Autowired
	private AnexosMedicaoSsAntigoRepository anexosMedicaoSsAntigoRepository;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private AuditoriaService auditoriaService;
	@Autowired
	private AnexosMedicaoSsMapper anexosMedicaoSsMapper;
	@Autowired
	private AnexosMedicaoSsAntigoMapper anexosMedicaoSsAntigoMapper;
	
	@Override
	public void salvar(AnexosMedicaoSsDTO anexosMedicaoSsDTO, String token) {
		
		SolicitacaoServicoDTO ssDTO= this.buscarSS(anexosMedicaoSsDTO);
		if(ssDTO.isFlagExclusao())
			 throw new MsgGenericaPersonalizadaException("Essa SS foi excluída.Não pode ser incluído um anexo");
		
		AnexosMedicaoSs anexosMedicaoSs = new AnexosMedicaoSs();
		byte[] arquivo= Base64.decodeBase64(anexosMedicaoSsDTO.getData());
		 

		anexosMedicaoSsDTO.setRefAtendimento("20" + anexosMedicaoSsDTO.getRefAtendimento().substring(3, 5) + anexosMedicaoSsDTO.getRefAtendimento().substring(0, 2));
		Integer tamanhoArquivos = anexosMedicaoSsRepository
				.buscarPorSSResumido(anexosMedicaoSsDTO.getCodAtendimento(), Integer.parseInt(anexosMedicaoSsDTO.getRefAtendimento()),
						anexosMedicaoSsDTO.getSeqSS())
				.stream().mapToInt(e -> e.getTamanhoArquivo()).sum() + arquivo.length;
		
		    ValidarArquivo.validarTotalArquivo(tamanhoArquivos);
		    ValidarArquivo.validarExtensaoArquivo(anexosMedicaoSsDTO.getNomeArquivo(), arquivo);
		    ValidarArquivo.validarTamanhoArquivo(arquivo.length);
			
		
			anexosMedicaoSs.setArquivo(arquivo);
			anexosMedicaoSs.setNomeArquivo(anexosMedicaoSsDTO.getNomeArquivo());
			anexosMedicaoSs.setTamanhoArquivo(arquivo.length);		
			anexosMedicaoSs.setCodAtendimento(anexosMedicaoSsDTO.getCodAtendimento());
			anexosMedicaoSs.setRefAtendimento(Integer.parseInt(anexosMedicaoSsDTO.getRefAtendimento()));
			anexosMedicaoSs.setSeqSS(anexosMedicaoSsDTO.getSeqSS());
			anexosMedicaoSs.setDescricao(anexosMedicaoSsDTO.getDescricao());
			anexosMedicaoSs.setUsuario(anexosMedicaoSsDTO.getUsuario());
			anexosMedicaoSs.setAcessoRestrito(anexosMedicaoSsDTO.getAcessoRestrito());
			anexosMedicaoSsRepository.save(anexosMedicaoSs);

		String anexosMedicaoSsJson = anexosMedicaoSsMapper.toDto(anexosMedicaoSs).toJson();

		auditoriaService.gerarAuditoria(ssDTO.getId(), Constants.EMPTY_STRING, anexosMedicaoSsJson, 30L,
				"Medição SS", jwtTokenProvider.buscarIdUsuario(token)); 

		
	}
	
	@Override
	public AnexosMedicaoSsWrapperDTO buscarPorIdSs(Integer cdAtendimento, String refAtendimentoFormatada, Short seqSS,
			Pageable pageable) {

		AnexosMedicaoSsWrapperDTO anexosMedicaoSsWrapperDTO = new AnexosMedicaoSsWrapperDTO();
		Integer refAtendimento = Integer
				.parseInt("20" + refAtendimentoFormatada.substring(3, 5) + refAtendimentoFormatada.substring(0, 2));
		
		List<AnexosMedicaoSsDTO> listAnexosMedicaoSsDTO= anexosMedicaoSsRepository.buscarPorSS(cdAtendimento, refAtendimento, seqSS);
		Integer idMedicao=this.buscarIdMedicao(cdAtendimento,refAtendimento,seqSS);
		
		if (idMedicao != null)
			listAnexosMedicaoSsDTO.addAll(anexosMedicaoSsAntigoRepository.buscarPorSS(idMedicao.longValue()).stream()
					.peek(e -> e.setAnexoAntigo(true)).collect(Collectors.toList()));
		
		anexosMedicaoSsWrapperDTO.setTotalRegistro(listAnexosMedicaoSsDTO.size());
		String ordenarCampo = "";
		String ordem = "";
		if (pageable.getSort() != null && pageable.getSort().iterator().hasNext()) {
			Order order = pageable.getSort().iterator().next();
			ordenarCampo = order.getProperty();
			ordem = (order.isAscending() ? "asc" : "desc");
		}
		Collections.sort(listAnexosMedicaoSsDTO, new AnexosMedicaoSsDTOComparator(ordenarCampo, ordem));
		int indice = pageable.getPageSize() * pageable.getPageNumber();
		anexosMedicaoSsWrapperDTO.setListAnexosSolicitacaoServicoDTO(listAnexosMedicaoSsDTO.stream().skip(indice)
				.limit(pageable.getPageSize()).collect(Collectors.toList()));
		return anexosMedicaoSsWrapperDTO;
	}
	
	@Override
	public DownloadArquivoDTO buscarArquivoPorId(Long id, boolean anexoAntigo) {
	
		
		DownloadArquivoDTO downloadArquivoDTO = new DownloadArquivoDTO();
		
		AnexosMedicaoSsDTO anexosMedicaoSsDTO =this.buscaArquivoPorIdNovoOuAntigo(id, anexoAntigo);

		downloadArquivoDTO.setData(Base64.encodeBase64String(anexosMedicaoSsDTO.getArquivo()));
		downloadArquivoDTO.setNomeArquivo(anexosMedicaoSsDTO.getNomeArquivo());
		return downloadArquivoDTO;
		
	}
	@Override
	public void excluir(Long id, boolean anexoAntigo, String token) {
		String anexoMedSsJson="";
		Long idSs=null;
		if(anexoAntigo) {
          AnexosMedicaoSsAntigo anexosMedicaoSsAntigo=  this.validarExclusao(anexosMedicaoSsAntigoRepository.findById(id));
           
          anexoMedSsJson= anexosMedicaoSsAntigoMapper.toDto(anexosMedicaoSsAntigo).toJson();
          idSs=this.buscarSsPorIdMedicao(anexosMedicaoSsAntigo.getIdSolicitacaoServico().intValue()).getId();
          anexosMedicaoSsAntigoRepository.delete(anexosMedicaoSsAntigo);
		}else {
			  AnexosMedicaoSs anexosMedicaoSs= this.validarExclusao(anexosMedicaoSsRepository.findById(id));
			  AnexosMedicaoSsDTO anexosMedicaoSsDTO=anexosMedicaoSsMapper.toDto(anexosMedicaoSs);	  
	          anexoMedSsJson= anexosMedicaoSsDTO.toJson();
	          anexosMedicaoSsDTO.setRefAtendimento(this.formatarRefAtendimento(anexosMedicaoSsDTO.getRefAtendimento()));
	          idSs= this.buscarSS(anexosMedicaoSsDTO).getId();
	          anexosMedicaoSsRepository.delete(anexosMedicaoSs);
			  
		}
		
		auditoriaService.gerarAuditoria(idSs, anexoMedSsJson,
				Constants.EMPTY_STRING, 30L, "Medição SS", jwtTokenProvider.buscarIdUsuario(token));
		
	}
	
	@Override
	public void atualizar(AnexosMedicaoSsDTO anexosMedicaoSsDTO, String token) {
		SolicitacaoServicoDTO ssDTO= this.buscarSS(anexosMedicaoSsDTO);
		if(ssDTO.isFlagExclusao())
			 throw new MsgGenericaPersonalizadaException("Essa SS foi excluída.Não pode ser alterado o anexo");
		
		byte[] arquivo= Base64.decodeBase64(anexosMedicaoSsDTO.getData());
		
		anexosMedicaoSsDTO.setRefAtendimento("20" + anexosMedicaoSsDTO.getRefAtendimento().substring(3, 5) + anexosMedicaoSsDTO.getRefAtendimento().substring(0, 2));
		Integer tamanhoArquivos = anexosMedicaoSsRepository
				.buscarPorSSResumido(anexosMedicaoSsDTO.getCodAtendimento(), Integer.parseInt(anexosMedicaoSsDTO.getRefAtendimento()),
						anexosMedicaoSsDTO.getSeqSS())
				.stream().mapToInt(e -> e.getTamanhoArquivo()).sum() + arquivo.length;
		
		ValidarArquivo.validarTotalArquivo(tamanhoArquivos);
		ValidarArquivo.validarExtensaoArquivo(anexosMedicaoSsDTO.getNomeArquivo(), arquivo);
		ValidarArquivo.validarTamanhoArquivo(arquivo.length);
			
			if(anexosMedicaoSsDTO.isAnexoAntigo())
				this.atualizarAntigo(anexosMedicaoSsDTO, token, arquivo,ssDTO.getId());
			else
				this.atualizarNovo(anexosMedicaoSsDTO, token, arquivo,ssDTO.getId());
	}
	
	@Override
	public AnexosMedicaoSsDTO buscarPorId(Long id, boolean anexoAntigo) {
		AnexosMedicaoSsDTO anexosMedicaoSsDTO=this.buscaArquivoPorIdNovoOuAntigo(id, anexoAntigo);
		anexosMedicaoSsDTO.setAnexoAntigo(anexoAntigo);
		anexosMedicaoSsDTO.setData(this.buscarArquivoPorId(id, anexoAntigo).getData());
		return anexosMedicaoSsDTO;
	}
	
	@Override
	public void excluirTodosPorSs(Long idMedicao, Integer refAtendimento, Integer cdAtendimento, Short seqSS,Integer idSs,String token) {
		this.excluirAnexoNovo(refAtendimento, cdAtendimento, seqSS, idSs, token);	
		if(idMedicao!=null)
			this.excluirAnexoAntigo(idMedicao, idSs, token);
	}
	
	@Override
	public List<AnexosMedicaoSsDTO> buscarPorIdSs(Integer cdAtendimento, String refAtendimentoFormatada, Short seqSS) {
		Integer refAtendimento = Integer
				.parseInt("20" + refAtendimentoFormatada.substring(3, 5) + refAtendimentoFormatada.substring(0, 2));
		
		return anexosMedicaoSsRepository.buscarPorSS(cdAtendimento, refAtendimento, seqSS);
	}
	
	@Override
	public List<AnexosMedicaoSsDTO> buscarPorIdSsAll(Integer cdAtendimento, String refAtendimentoFormatada, Short seqSS) {
		Integer refAtendimento = Integer
				.parseInt("20" + refAtendimentoFormatada.substring(3, 5) + refAtendimentoFormatada.substring(0, 2));
		
		return anexosMedicaoSsRepository.buscarPorSSAll(cdAtendimento, refAtendimento);
	}
	
	@Override
	public List<AnexosMedicaoSsDTO> buscarAnexoAntigoPorIdSs(Integer cdAtendimento, String refAtendimentoFormatada,
			Short seqSS) {
		 List<AnexosMedicaoSsDTO> listAnexosMedicaoSsDTO= new ArrayList<>();
		Integer refAtendimento = Integer
				.parseInt("20" + refAtendimentoFormatada.substring(3, 5) + refAtendimentoFormatada.substring(0, 2));
		Integer idMedicao=this.buscarIdMedicao(cdAtendimento,refAtendimento,seqSS);
		
		if(idMedicao!=null)
			listAnexosMedicaoSsDTO=anexosMedicaoSsAntigoRepository.buscarPorSS(idMedicao.longValue());
		
		return listAnexosMedicaoSsDTO;
	}


	private void excluirAnexoNovo(Integer refAtendimento, Integer cdAtendimento, Short seqSS,Integer idSs,String token) {
		Long idUsuario=jwtTokenProvider.buscarIdUsuario(token);
		List<AnexosMedicaoSsDTO> listAnexosMedicaoSsDTO= anexosMedicaoSsRepository.buscarPorSS(cdAtendimento, refAtendimento, seqSS);
		for(AnexosMedicaoSsDTO anexosMedicaoSsDTO:listAnexosMedicaoSsDTO) {
			anexosMedicaoSsRepository.deleteById(anexosMedicaoSsDTO.getId());
			auditoriaService.gerarAuditoria(idSs.longValue(), anexosMedicaoSsDTO.toJson(),
					Constants.EMPTY_STRING, 30L, "Dados SS", idUsuario);
		}
	}
	
	private void excluirAnexoAntigo(Long idMedicao,Integer idSs,String token) {
		Long idUsuario=jwtTokenProvider.buscarIdUsuario(token);
		List<AnexosMedicaoSsDTO> listAnexosMedicaoSsDTO= anexosMedicaoSsAntigoRepository.buscarPorSS(idMedicao);
		for(AnexosMedicaoSsDTO anexosMedicaoSsDTO:listAnexosMedicaoSsDTO) {
			anexosMedicaoSsAntigoRepository.deleteById(anexosMedicaoSsDTO.getId());
			auditoriaService.gerarAuditoria(idSs.longValue(), anexosMedicaoSsDTO.toJson(),
					Constants.EMPTY_STRING, 30L, "Dados SS", idUsuario);
		}
	}

	
	private AnexosMedicaoSsDTO buscaArquivoPorIdNovoOuAntigo(Long id, boolean anexoAntigo) {
		if(anexoAntigo)
			return anexosMedicaoSsAntigoMapper.toDto(anexosMedicaoSsAntigoRepository.findById(id).get());
		else 
			return anexosMedicaoSsMapper.toDto(anexosMedicaoSsRepository.findById(id).get());
		
	}

	
	private SolicitacaoServicoDTO buscarSS(AnexosMedicaoSsDTO anexosMedicaoSsDTO) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		RestTemplate restTemplate = new RestTemplate();
		String url = urlMicroservico.getUrlServico() + "/backend-servico/solicitacaoServico";
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("refAtendimento", anexosMedicaoSsDTO.getRefAtendimento())
				.queryParam("codAtendimento", anexosMedicaoSsDTO.getCodAtendimento()).queryParam("ssSequencial", anexosMedicaoSsDTO.getSeqSS());
		HttpEntity<?> request = new HttpEntity<>(headers);
		 try {
		HttpEntity<SolicitacaoServicoDTO> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, request,
				SolicitacaoServicoDTO.class);
		
		
		return response.getBody();
		 }catch ( HttpClientErrorException httpClientErrorException) {
		        throw new MsgGenericaPersonalizadaException("SS não existe");
		  }
		
		
	}
	private SolicitacaoServicoDTO buscarSsPorIdMedicao(Integer idMedicao) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		RestTemplate restTemplate = new RestTemplate();
		String url = urlMicroservico.getUrlServico() + "/backend-servico/medicaoSolicitacaoServico/ss/medicaoSs/"+idMedicao.toString();
		HttpEntity<?> request = new HttpEntity<>(headers);
		 try {
		HttpEntity<SolicitacaoServicoDTO> response = restTemplate.exchange(url, HttpMethod.GET, request,
				SolicitacaoServicoDTO.class);
		
		
		return response.getBody();
		 }catch ( HttpClientErrorException httpClientErrorException) {
		        throw new MsgGenericaPersonalizadaException("SS não existe");
		  }
		
	}
	
	private Integer buscarIdMedicao(Integer cdAtendimento, Integer refAtendimento, Short seqSS) {
		String refAtendimentoFormatado=refAtendimento.toString().substring(4, 6)+"/"+refAtendimento.toString().substring(2,4);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		RestTemplate restTemplate = new RestTemplate();
		String url = urlMicroservico.getUrlServico() + "/backend-servico/medicaoSolicitacaoServico/medicao";
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("refAtendimento", refAtendimentoFormatado)
				.queryParam("codAtendimento", cdAtendimento).queryParam("ssSequencial",seqSS);
		HttpEntity<?> request = new HttpEntity<>(headers);
		 try {
		HttpEntity<MedicaoSolicitacaoServicoComplDTO> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, request,
				MedicaoSolicitacaoServicoComplDTO.class);
		
		return response.getBody().getIdMedicaoSs();
		
		 }catch ( HttpClientErrorException httpClientErrorException) {
		        throw new MsgGenericaPersonalizadaException("SS não existe");
		  }
		
		
	}

	private <T> T  validarExclusao(Optional<T> anexoMedSs) {
		if(anexoMedSs.isEmpty())
      	  throw new MsgGenericaPersonalizadaException("Exclusão inválida");
		else
		 return anexoMedSs.get();	
	}
	
	private void atualizarAntigo(AnexosMedicaoSsDTO anexosMedicaoSsDTO, String token,byte[] arquivo,Long idSs) {
		AnexosMedicaoSsAntigo anexosMedicaoSsAntigo= anexosMedicaoSsAntigoRepository.findById(anexosMedicaoSsDTO.getId()).get();
		String anexosMedicaoSsJsonAntes = anexosMedicaoSsAntigoMapper.toDto(anexosMedicaoSsAntigo).toJson();
		
		anexosMedicaoSsAntigo.setArquivo(arquivo);
		anexosMedicaoSsAntigo.setNomeArquivo(anexosMedicaoSsDTO.getNomeArquivo());	
		anexosMedicaoSsAntigo.setDescricao(anexosMedicaoSsDTO.getDescricao());
		anexosMedicaoSsAntigo.setUsuario(anexosMedicaoSsDTO.getUsuario());
		
		String anexosMedicaoSsJsonDepois=anexosMedicaoSsAntigoMapper.toDto(anexosMedicaoSsAntigo).toJson();
		anexosMedicaoSsAntigoRepository.save(anexosMedicaoSsAntigo);

		auditoriaService.gerarAuditoria(idSs, anexosMedicaoSsJsonAntes, anexosMedicaoSsJsonDepois, 30L,
				"Medição SS", jwtTokenProvider.buscarIdUsuario(token)); 
	}
	
	private void atualizarNovo(AnexosMedicaoSsDTO anexosMedicaoSsDTO, String token,byte[] arquivo,Long idSs) {
		AnexosMedicaoSs anexosMedicaoSs= anexosMedicaoSsRepository.findById(anexosMedicaoSsDTO.getId()).get();
		String anexosMedicaoSsJsonAntes = anexosMedicaoSsMapper.toDto(anexosMedicaoSs).toJson();
		
		anexosMedicaoSs.setArquivo(arquivo);
		anexosMedicaoSs.setNomeArquivo(anexosMedicaoSsDTO.getNomeArquivo());
		anexosMedicaoSs.setTamanhoArquivo(arquivo.length);		
		anexosMedicaoSs.setCodAtendimento(anexosMedicaoSsDTO.getCodAtendimento());
		anexosMedicaoSs.setRefAtendimento(Integer.parseInt(anexosMedicaoSsDTO.getRefAtendimento()));
		anexosMedicaoSs.setSeqSS(anexosMedicaoSsDTO.getSeqSS());
		anexosMedicaoSs.setDescricao(anexosMedicaoSsDTO.getDescricao());
		anexosMedicaoSs.setUsuario(anexosMedicaoSsDTO.getUsuario());
		anexosMedicaoSs.setAcessoRestrito(anexosMedicaoSsDTO.getAcessoRestrito());
		
		String anexosMedicaoSsJsonDepois = anexosMedicaoSsMapper.toDto(anexosMedicaoSs).toJson();
		anexosMedicaoSsRepository.save(anexosMedicaoSs);
		
		
		auditoriaService.gerarAuditoria(idSs, anexosMedicaoSsJsonAntes, anexosMedicaoSsJsonDepois, 30L,
				"Medição SS", jwtTokenProvider.buscarIdUsuario(token)); 
		
	}

	private String formatarRefAtendimento(String refAtendimento) {
		return refAtendimento.substring(4, 6)+"/"+refAtendimento.substring(2, 4);
	}



	
	

	
	
	
}
