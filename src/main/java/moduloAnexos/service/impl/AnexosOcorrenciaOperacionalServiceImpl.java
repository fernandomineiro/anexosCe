package moduloAnexos.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import moduloAnexos.autenticacao.JwtTokenProvider;
import moduloAnexos.excecoes.MsgGenericaPersonalizadaException;
import moduloAnexos.model.AnexosOcorrenciaOperacional;
import moduloAnexos.repository.AnexosOcorrenciaOperacionalRepository;
import moduloAnexos.service.AnexosOcorrenciaOperacionalService;
import moduloAnexos.service.AuditoriaService;
import moduloAnexos.service.dto.AnexosArquivoOcorrenciaOperacionalDTO;
import moduloAnexos.service.dto.AnexosOcorrenciaOperacionalDTO;
import moduloAnexos.service.dto.AnexosOcorrenciaOperacionalWrapperDTO;
import moduloAnexos.service.dto.DownloadArquivoDTO;
import moduloAnexos.service.dto.OcorrenciaOperacionalDTO;
import moduloAnexos.service.mapper.AnexosArquivoOcorrenciaOperacionalMapper;
import moduloAnexos.service.mapper.AnexosOcorrenciaOperacionalMapper;
import moduloAnexos.util.Constants;
import moduloAnexos.util.UrlMicroservico;

@Service
@Transactional
public class AnexosOcorrenciaOperacionalServiceImpl implements AnexosOcorrenciaOperacionalService {

	@Autowired
	private AnexosOcorrenciaOperacionalRepository anexosOcorrenciaOperacionalRepository;
	
	@Autowired
	private AnexosOcorrenciaOperacionalMapper anexosOcorrenciaOperacionalMapper;

	@Autowired
	private AnexosArquivoOcorrenciaOperacionalMapper anexosArquivoOcorrenciaOperacionalMapper;
	
	@Autowired
	private UrlMicroservico urlMicroservico;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private AuditoriaService auditoriaService;

	@Override
	public AnexosOcorrenciaOperacionalDTO salvar(AnexosOcorrenciaOperacionalDTO anexosOcorrenciaOperacionalDTO, String token) {

		OcorrenciaOperacionalDTO ocorrenciaOperacional = this.buscarOcorrenciaOperacional(anexosOcorrenciaOperacionalDTO.getIdOcorrenciaOperacional());

		if (ocorrenciaOperacional == null) {
			throw new MsgGenericaPersonalizadaException("Ocorrência Operacional não existe");
		}
		
		if (ocorrenciaOperacional.getMaint().equalsIgnoreCase("D")) {
			throw new MsgGenericaPersonalizadaException("Ocorrência Operacional foi excluída");
		}
		
		Optional<AnexosOcorrenciaOperacional> anexosOcorrenciaOperacionalOp = anexosOcorrenciaOperacionalRepository.findById(anexosOcorrenciaOperacionalDTO.getId());

		if (anexosOcorrenciaOperacionalOp.isEmpty()) {
			throw new MsgGenericaPersonalizadaException("Id não existe na base ou foi excluído");
		}

		AnexosOcorrenciaOperacional anexosOcorrenciaOperacional = anexosOcorrenciaOperacionalOp.get();
		anexosOcorrenciaOperacional.setIdOcorrenciaOperacional(anexosOcorrenciaOperacionalDTO.getIdOcorrenciaOperacional());
		anexosOcorrenciaOperacional.setDescricao(anexosOcorrenciaOperacionalDTO.getDescricao());
		anexosOcorrenciaOperacional.setUsuario(anexosOcorrenciaOperacionalDTO.getUsuario());
		anexosOcorrenciaOperacionalRepository.save(anexosOcorrenciaOperacional);

		String anexosOcorrenciaOperacionalJson = anexosOcorrenciaOperacionalMapper.toDto(anexosOcorrenciaOperacional).toJson();

		auditoriaService.gerarAuditoria(anexosOcorrenciaOperacional.getId().longValue(), Constants.EMPTY_STRING, anexosOcorrenciaOperacionalJson,
				24L, "AnexosOcorrenciaOperacional", jwtTokenProvider.buscarIdUsuario(token));

		return anexosOcorrenciaOperacionalMapper.toDto(anexosOcorrenciaOperacional);
	}

	@Override
	public void excluir(Long id, String token) {
		Optional<AnexosOcorrenciaOperacional> anexosOcorrenciaOperacional = anexosOcorrenciaOperacionalRepository.findById(id);

		if (anexosOcorrenciaOperacional.isEmpty()) {
			throw new MsgGenericaPersonalizadaException("Exclusão inválida");
		}

		OcorrenciaOperacionalDTO OcorrenciaOperacional = this.buscarOcorrenciaOperacional(anexosOcorrenciaOperacional.get().getIdOcorrenciaOperacional());

		if (OcorrenciaOperacional.getMaint().equalsIgnoreCase("D")) {
			throw new MsgGenericaPersonalizadaException("Ocorrência Operacional foi excluída");
		}
		
		anexosOcorrenciaOperacionalRepository.delete(anexosOcorrenciaOperacional.get());

		String anexosOcorrenciaOperacionalJson = anexosOcorrenciaOperacionalMapper.toDto(anexosOcorrenciaOperacional.get()).toJson();

		auditoriaService.gerarAuditoria(anexosOcorrenciaOperacional.get().getId().longValue(), anexosOcorrenciaOperacionalJson,
				Constants.EMPTY_STRING, 24L, "AnexosOcorrenciaOperacional", jwtTokenProvider.buscarIdUsuario(token));
	}

	@Override
	public AnexosOcorrenciaOperacionalDTO buscarPorId(Long id) {

		Optional<AnexosOcorrenciaOperacional> anexosOcorrenciaOperacional = anexosOcorrenciaOperacionalRepository.findById(id);

		if (anexosOcorrenciaOperacional.isEmpty()) {
			throw new MsgGenericaPersonalizadaException("Anexo Ocorrência Operacional não existe ou foi excluído");
		}

		return anexosOcorrenciaOperacionalMapper.toDto(anexosOcorrenciaOperacional.get());
	}

	@Override
	public DownloadArquivoDTO buscarArquivoPorId(Long id) {
		Optional<AnexosOcorrenciaOperacional> anexosOcorrenciaOperacional = anexosOcorrenciaOperacionalRepository.findById(id);
		DownloadArquivoDTO downloadArquivoDTO = new DownloadArquivoDTO();

		if (anexosOcorrenciaOperacional.isEmpty()) {
			throw new MsgGenericaPersonalizadaException("Anexo Ocorrência Operacional não existe ou foi excluído");
		}

		downloadArquivoDTO.setData(Base64.encodeBase64String(anexosOcorrenciaOperacional.get().getArquivo()));
		downloadArquivoDTO.setNomeArquivo(anexosOcorrenciaOperacional.get().getNomeArquivo());
		return downloadArquivoDTO;
	}

	@Override
	public AnexosOcorrenciaOperacionalWrapperDTO buscarPorCodigoOcorrenciaOperacional(Long idOcorrenciaOperacional, Pageable pageable) {
		AnexosOcorrenciaOperacionalWrapperDTO anexosOcorrenciaOperacionalWrapper = new AnexosOcorrenciaOperacionalWrapperDTO();
		anexosOcorrenciaOperacionalWrapper
				.setListAnexosOcorrenciaOperacionalDTO(anexosOcorrenciaOperacionalRepository.buscarPorId(idOcorrenciaOperacional, pageable));
		anexosOcorrenciaOperacionalWrapper.setTotalRegistros(anexosOcorrenciaOperacionalRepository.countByIdOcorrenciaOperacional(idOcorrenciaOperacional));
		
		OcorrenciaOperacionalDTO OcorrenciaOperacional = this.buscarOcorrenciaOperacional(idOcorrenciaOperacional);
		
		if (OcorrenciaOperacional.getMaint().equalsIgnoreCase("D")) {
			anexosOcorrenciaOperacionalWrapper.setRegistroExcluido(Boolean.TRUE);
		}

		return anexosOcorrenciaOperacionalWrapper;
	}

	@Override
	public AnexosArquivoOcorrenciaOperacionalDTO salvarArquivo(MultipartFile arquivo, Long idOcorrenciaOperacional) {
		AnexosOcorrenciaOperacional anexosOcorrenciaOperacional = new AnexosOcorrenciaOperacional();

		try {
			this.validarExtensaoArquivo(arquivo.getOriginalFilename());
			this.validarTamanhoArquivo(arquivo.getBytes().length, idOcorrenciaOperacional);
			anexosOcorrenciaOperacional.setArquivo(arquivo.getBytes());
			anexosOcorrenciaOperacional.setNomeArquivo(arquivo.getOriginalFilename());

			anexosOcorrenciaOperacional = anexosOcorrenciaOperacionalRepository.save(anexosOcorrenciaOperacional);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}

		return anexosArquivoOcorrenciaOperacionalMapper.toDto(anexosOcorrenciaOperacional);
	}

	private void validarExtensaoArquivo(String nomeArquivo) {
		int indice = nomeArquivo.lastIndexOf(".");
		String extensaoArquivo = nomeArquivo.substring(indice + 1, nomeArquivo.length());
		if (this.buscarExtensoesInvalidas().stream().anyMatch(e -> e.equalsIgnoreCase(extensaoArquivo)))
			throw new MsgGenericaPersonalizadaException("Arquivo inválido");
	}

	private List<String> buscarExtensoesInvalidas() {
		List<String> listExtensao = new ArrayList<>();
		listExtensao.add("sql");
		listExtensao.add("exe");
		listExtensao.add("js");
		listExtensao.add("bin");
		listExtensao.add("sh");
		listExtensao.add("bat");
		listExtensao.add("json");
		listExtensao.add("xml");
		listExtensao.add("rdl");
		listExtensao.add("zip");
		listExtensao.add("msi");

		return listExtensao;
	}

	private void validarTamanhoArquivo(int tamanhoArquivo, Long idOcorrenciaOperacional) {
		List<AnexosOcorrenciaOperacional> arquivos = anexosOcorrenciaOperacionalRepository.findAllByIdOcorrenciaOperacional(idOcorrenciaOperacional);
		int tamanhoArquivoMaximo = 1024 * 50000;
		
		for (AnexosOcorrenciaOperacional a : arquivos) {
			tamanhoArquivo += a.getArquivo().length;
		}
		
		if (tamanhoArquivo > tamanhoArquivoMaximo) {
			throw new MsgGenericaPersonalizadaException("Este arquivo ultrapassa o limite de 50mb de anexos por Ocorrência Operacional.");
		}
	}
	
	private OcorrenciaOperacionalDTO buscarOcorrenciaOperacional(Long idOcorrenciaOperacional) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		RestTemplate restTemplate = new RestTemplate();
		String url = urlMicroservico.getUrlServico() + "/backend-servico/ocorrenciaOperacional/" + idOcorrenciaOperacional;
		HttpEntity<?> request = new HttpEntity<>(headers);
		HttpEntity<OcorrenciaOperacionalDTO> response = restTemplate.exchange(url, HttpMethod.GET, request, OcorrenciaOperacionalDTO.class);
		return response.getBody();
	}

}
