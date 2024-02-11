package moduloAnexos.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import moduloAnexos.autenticacao.JwtTokenProvider;
import moduloAnexos.excecoes.MsgGenericaPersonalizadaException;
import moduloAnexos.model.AnexosSolicitacaoServico;
import moduloAnexos.model.IdRegraUsuarioAnexos;
import moduloAnexos.model.RegraUsuarioAnexos;
import moduloAnexos.repository.AnexosSolicitacaoServicoRepository;
import moduloAnexos.repository.RegraUsuarioAnexosRepository;
import moduloAnexos.service.AnexosSolicitacaoServicoService;
import moduloAnexos.service.AuditoriaService;
import moduloAnexos.service.dto.AnexosSolicitacaoServicoDTO;
import moduloAnexos.service.dto.AnexosSolicitacaoServicoWrapperDTO;
import moduloAnexos.service.dto.DownloadArquivoDTO;
import moduloAnexos.service.dto.SolicitacaoServicoDTO;
import moduloAnexos.service.mapper.AnexosSolicitacaoServicoMapper;
import moduloAnexos.util.Constants;
import moduloAnexos.util.UrlMicroservico;
import moduloAnexos.util.ValidarArquivo;

@Service
@Transactional
public class AnexosSolicitacaoServicoServiceImpl implements AnexosSolicitacaoServicoService {


	@Autowired
	private AnexosSolicitacaoServicoRepository anexosSolicitacaoServicoRepository;
	@Autowired
	private RegraUsuarioAnexosRepository regraUsuarioAnexosRepository;
	@Autowired
	private AnexosSolicitacaoServicoMapper anexosSolicitacaoServicoMapper;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private AuditoriaService auditoriaService;
	@Autowired
	private UrlMicroservico urlMicroservico;
	
	@Override
	public AnexosSolicitacaoServicoDTO salvar(AnexosSolicitacaoServicoDTO anexosSolicitacaoServicoDTO, String token) {
		if(!this.validarSS(anexosSolicitacaoServicoDTO))
		 throw new MsgGenericaPersonalizadaException("Essa SS foi excluída.Não pode ser incluído um anexo");
		
		return this.salvar(anexosSolicitacaoServicoDTO,jwtTokenProvider.buscarIdUsuario(token));
	}
	
	@Override
	public AnexosSolicitacaoServicoDTO salvar(AnexosSolicitacaoServicoDTO anexosSolicitacaoServicoDTO,
			Long idUsuario) {
		AnexosSolicitacaoServico anexosSolicitacaoServico = new AnexosSolicitacaoServico();
		byte[] arquivo= Base64.decodeBase64(anexosSolicitacaoServicoDTO.getData());
		 
		Integer tamanhoArquivos = anexosSolicitacaoServicoRepository
				.buscarPorSS(anexosSolicitacaoServicoDTO.getCodAtendimento(), anexosSolicitacaoServicoDTO.getRefAtendimento(),
						anexosSolicitacaoServicoDTO.getSeqSS())
				.stream().mapToInt(e -> e.getTamanhoArquivo()).sum() + arquivo.length;
		
		ValidarArquivo.validarTotalArquivo(tamanhoArquivos);
		ValidarArquivo.validarExtensaoArquivo(anexosSolicitacaoServicoDTO.getNomeArquivo(), arquivo);
		ValidarArquivo.validarTamanhoArquivo(arquivo.length);
			
		
		anexosSolicitacaoServico.setArquivo(arquivo);
		anexosSolicitacaoServico.setNomeArquivo(anexosSolicitacaoServicoDTO.getNomeArquivo());
		anexosSolicitacaoServico.setTamanhoArquivo(arquivo.length);		
		anexosSolicitacaoServico.setCodAtendimento(anexosSolicitacaoServicoDTO.getCodAtendimento());
		anexosSolicitacaoServico.setRefAtendimento(anexosSolicitacaoServicoDTO.getRefAtendimento());
		anexosSolicitacaoServico.setSeqSS(anexosSolicitacaoServicoDTO.getSeqSS());
		anexosSolicitacaoServico.setDescricao(anexosSolicitacaoServicoDTO.getDescricao());
		anexosSolicitacaoServico.setUsuario(anexosSolicitacaoServicoDTO.getUsuario());
		anexosSolicitacaoServicoRepository.save(anexosSolicitacaoServico);

		String anexosSolicitacaoServicoJson = anexosSolicitacaoServicoMapper.toDto(anexosSolicitacaoServico).toJson();

		auditoriaService.gerarAuditoria(anexosSolicitacaoServico.getId().longValue(), Constants.EMPTY_STRING, anexosSolicitacaoServicoJson, 30L,
				"Anexos SS", idUsuario); 

		return anexosSolicitacaoServicoMapper.toDto(anexosSolicitacaoServico);
	}


	@Override
	public void excluir(Long id, String token) {
		Optional<AnexosSolicitacaoServico> anexosSolicitacaoServico = anexosSolicitacaoServicoRepository.findById(id);
		
		if (!this.validarExclusao(token)) {
			throw new MsgGenericaPersonalizadaException("Não possui permissão para excluir anexos");
		}
		
	
		if (anexosSolicitacaoServico.isEmpty()) {

			throw new MsgGenericaPersonalizadaException("Exclusão inválida");
		}

		if(!this.validarSS(anexosSolicitacaoServicoMapper.toDto(anexosSolicitacaoServico.get())))
			 throw new MsgGenericaPersonalizadaException("Essa SS foi excluída.Não pode ser excluído um anexo");


		anexosSolicitacaoServicoRepository.delete(anexosSolicitacaoServico.get());

		String anexosSolicitacaoServicoJson = anexosSolicitacaoServicoMapper.toDto(anexosSolicitacaoServico.get()).toJson();

		auditoriaService.gerarAuditoria(anexosSolicitacaoServico.get().getId().longValue(), anexosSolicitacaoServicoJson,
				Constants.EMPTY_STRING, 30L, "Anexos SS", jwtTokenProvider.buscarIdUsuario(token));
		
	}

	@Override
	public AnexosSolicitacaoServicoDTO buscarPorId(Long id) {
		Optional<AnexosSolicitacaoServico> anexosSolicitacaoServico = anexosSolicitacaoServicoRepository.findById(id);

		if (anexosSolicitacaoServico.isEmpty()) {

			throw new MsgGenericaPersonalizadaException("Anexo Imovel não existe ou foi excluído");
		}
		return anexosSolicitacaoServicoMapper.toDto(anexosSolicitacaoServico.get());
	
	}

	@Override
	public AnexosSolicitacaoServicoWrapperDTO buscarPorId(Integer cdAtendimento, String refAtendimentoFormatada, Short seqSS,
			Pageable pageable) {
		Integer refAtendimento = Integer 
				.parseInt("20" + refAtendimentoFormatada.substring(3, 5) + refAtendimentoFormatada.substring(0, 2));
		AnexosSolicitacaoServicoWrapperDTO listAnexoSolicitacaoServicoDTO = new AnexosSolicitacaoServicoWrapperDTO();
		listAnexoSolicitacaoServicoDTO.setListAnexosSolicitacaoServicoDTO(
				anexosSolicitacaoServicoRepository.buscarPorSS(cdAtendimento, refAtendimento, seqSS, pageable));
		listAnexoSolicitacaoServicoDTO.setTotalRegistro(anexosSolicitacaoServicoRepository
				.countByCodAtendimentoAndRefAtendimentoAndSeqSS(cdAtendimento, refAtendimento, seqSS));
		listAnexoSolicitacaoServicoDTO.setTotalArquivo(anexosSolicitacaoServicoRepository
				.buscarPorSS(cdAtendimento, refAtendimento, seqSS).stream().mapToInt(e -> e.getTamanhoArquivo()).sum());
		return listAnexoSolicitacaoServicoDTO;
	}
	
	@Override
	/**
	 * Exemplo de formato do parêmetro refAtendimento
	 * refAtendimento = 202110
	 */
	public AnexosSolicitacaoServicoWrapperDTO buscarPorId(Integer cdAtendimento, Integer refAtendimento, Short seqSS,
			Pageable pageable) {
		
		AnexosSolicitacaoServicoWrapperDTO listAnexoSolicitacaoServicoDTO = new AnexosSolicitacaoServicoWrapperDTO();
		listAnexoSolicitacaoServicoDTO.setListAnexosSolicitacaoServicoDTO(
				anexosSolicitacaoServicoRepository.buscarPorSS(cdAtendimento, refAtendimento, seqSS, pageable));
		listAnexoSolicitacaoServicoDTO.setTotalRegistro(anexosSolicitacaoServicoRepository
				.countByCodAtendimentoAndRefAtendimentoAndSeqSS(cdAtendimento, refAtendimento, seqSS));
		listAnexoSolicitacaoServicoDTO.setTotalArquivo(anexosSolicitacaoServicoRepository
				.buscarPorSS(cdAtendimento, refAtendimento, seqSS).stream().mapToInt(e -> e.getTamanhoArquivo()).sum());
		return listAnexoSolicitacaoServicoDTO;
	}

	
	@Override
	public DownloadArquivoDTO buscarArquivoPorId(Long id) {
		Optional<AnexosSolicitacaoServico> anexosSolicitacaoServico = anexosSolicitacaoServicoRepository.findById(id);
		DownloadArquivoDTO downloadArquivoDTO = new DownloadArquivoDTO();
		

		if (anexosSolicitacaoServico.isEmpty()) {

			throw new MsgGenericaPersonalizadaException("Anexo de SS  não existe ou foi excluído");
		}
		downloadArquivoDTO.setData(Base64.encodeBase64String(anexosSolicitacaoServico.get().getArquivo()));
		downloadArquivoDTO.setNomeArquivo(anexosSolicitacaoServico.get().getNomeArquivo());
		return downloadArquivoDTO;
	}
	
	@Override
	public List<AnexosSolicitacaoServicoDTO> buscarPorId(Integer cdAtendimento, String refAtendimentoFormatada, Short seqSS) {
		Integer refAtendimento = Integer
				.parseInt("20" + refAtendimentoFormatada.substring(3, 5) + refAtendimentoFormatada.substring(0, 2));
		
		return anexosSolicitacaoServicoRepository.buscarPorSsSemPaginacao(cdAtendimento, refAtendimento, seqSS);
		
	}
	
	@Override
	public List<AnexosSolicitacaoServicoDTO> buscarPorIdAll(Integer cdAtendimento, String refAtendimentoFormatada, Short seqSS) {
		Integer refAtendimento = Integer
				.parseInt("20" + refAtendimentoFormatada.substring(3, 5) + refAtendimentoFormatada.substring(0, 2));
		
		return anexosSolicitacaoServicoRepository.buscarPorSsSemPaginacaoAll(cdAtendimento, refAtendimento);
		
	}

	private boolean validarExclusao(String token) {

		IdRegraUsuarioAnexos idRegraUsuarioAnexos = new IdRegraUsuarioAnexos();
		idRegraUsuarioAnexos.setIdUsuario(jwtTokenProvider.buscarIdUsuario(token).intValue());
		idRegraUsuarioAnexos.setIdregra(53);

		Optional<RegraUsuarioAnexos> regra = regraUsuarioAnexosRepository.findById(idRegraUsuarioAnexos);
		if (regra.isPresent()) 
			return true;
		else
			return false;

		

	}
	
	private boolean validarSS(AnexosSolicitacaoServicoDTO anexosSolicitacaoServicoDTO) {
		String refAtendimento=anexosSolicitacaoServicoDTO.getRefAtendimento().toString().substring(4, 6)+"/"+anexosSolicitacaoServicoDTO.getRefAtendimento().toString().substring(2,4);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		RestTemplate restTemplate = new RestTemplate();
		String url = urlMicroservico.getUrlServico() + "/backend-servico/solicitacaoServico";
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("refAtendimento", refAtendimento)
				.queryParam("codAtendimento", anexosSolicitacaoServicoDTO.getCodAtendimento()).queryParam("ssSequencial", anexosSolicitacaoServicoDTO.getSeqSS());
		HttpEntity<?> request = new HttpEntity<>(headers);
		 try {
		HttpEntity<SolicitacaoServicoDTO> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, request,
				SolicitacaoServicoDTO.class);
		if(response.getBody().isFlagExclusao())
			return false;
		else
			return true;
		 }catch ( HttpClientErrorException httpClientErrorException) {
		        throw new MsgGenericaPersonalizadaException("SS não existe");
		  }
		
		
	}

	


}
