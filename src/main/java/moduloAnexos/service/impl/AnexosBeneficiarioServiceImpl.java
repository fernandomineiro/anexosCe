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
import moduloAnexos.model.AnexosBeneficiario;
import moduloAnexos.model.AnexosCliente;
import moduloAnexos.repository.AnexosBeneficiarioRepository;
import moduloAnexos.repository.AnexosClienteRepository;
import moduloAnexos.service.AnexosBeneficiarioService;
import moduloAnexos.service.AnexosClienteService;
import moduloAnexos.service.AuditoriaService;
import moduloAnexos.service.dto.AnexosArquivoBeneficiarioDTO;
import moduloAnexos.service.dto.AnexosArquivoClienteDTO;
import moduloAnexos.service.dto.AnexosBeneficiarioDTO;
import moduloAnexos.service.dto.AnexosBeneficiarioWrapperDTO;
import moduloAnexos.service.dto.AnexosClienteDTO;
import moduloAnexos.service.dto.AnexosClienteWrapperDTO;
import moduloAnexos.service.dto.BeneficiarioDTO;
import moduloAnexos.service.dto.ClienteDTO;
import moduloAnexos.service.dto.DownloadArquivoDTO;
import moduloAnexos.service.mapper.AnexosArquivoBeneficiarioMapper;
import moduloAnexos.service.mapper.AnexosArquivoClienteMapper;
import moduloAnexos.service.mapper.AnexosBeneficiarioMapper;
import moduloAnexos.service.mapper.AnexosClienteMapper;
import moduloAnexos.util.Constants;
import moduloAnexos.util.Formulas;
import moduloAnexos.util.UrlMicroservico;

@Service
@Transactional
public class AnexosBeneficiarioServiceImpl implements AnexosBeneficiarioService {

	@Autowired
	private AnexosBeneficiarioRepository anexosBeneficiarioRepository;
	
	@Autowired
	private AnexosBeneficiarioMapper anexosBeneficiarioMapper;

	@Autowired
	private AnexosArquivoBeneficiarioMapper anexosArquivoBeneficiarioMapper;
	
	@Autowired
	private UrlMicroservico urlMicroservico;
	
	@Autowired
	private Formulas formulas;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private AuditoriaService auditoriaService;

	@Override
	public AnexosBeneficiarioDTO salvar(AnexosBeneficiarioDTO anexosBeneficiarioDTO, String token) {

//		ClienteDTO cliente = this.buscarCliente(anexosBeneficiarioDTO.getIdBeneficiario());
//
//		if (cliente == null) {
//			throw new MsgGenericaPersonalizadaException("Cliente não existe.");
//		}
//		
//		if (cliente.getMaint().equalsIgnoreCase("D")) {
//			throw new MsgGenericaPersonalizadaException("Cliente foi excluído");
//		}
		
		Optional<AnexosBeneficiario> anexosBeneficiarioOp = anexosBeneficiarioRepository.findById(anexosBeneficiarioDTO.getId());

		if (anexosBeneficiarioOp.isEmpty()) {
			throw new MsgGenericaPersonalizadaException("Id não existe na base ou foi excluído");
		}

		AnexosBeneficiario anexosBeneficiario = anexosBeneficiarioOp.get();
		anexosBeneficiario.setIdBeneficiario(anexosBeneficiarioDTO.getIdBeneficiario());
		anexosBeneficiario.setDescricao(anexosBeneficiarioDTO.getDescricao());
		anexosBeneficiario.setUsuario(anexosBeneficiarioDTO.getUsuario());
		anexosBeneficiarioRepository.save(anexosBeneficiario);

		String anexosBeneficiarioJson = anexosBeneficiarioMapper.toDto(anexosBeneficiario).toJson();

//		auditoriaService.gerarAuditoria(anexosBeneficiario.getId().longValue(), Constants.EMPTY_STRING, anexosBeneficiarioJson,
//				24L, "AnexosCliente", jwtTokenProvider.buscarIdUsuario(token));

		return anexosBeneficiarioMapper.toDto(anexosBeneficiario);
	}

	@Override
	public void excluir(Long id, String token) {
		Optional<AnexosBeneficiario> anexosBeneficiario = anexosBeneficiarioRepository.findById(id);

		if (anexosBeneficiario.isEmpty()) {
			throw new MsgGenericaPersonalizadaException("Exclusão inválida");
		}

//		ClienteDTO cliente = this.buscarCliente(anexosBeneficiario.get().getIdBeneficiario());
//
//		if (cliente.getMaint().equalsIgnoreCase("D")) {
//			throw new MsgGenericaPersonalizadaException("Cliente foi excluído");
//		}
		
		anexosBeneficiarioRepository.delete(anexosBeneficiario.get());

		String anexosClienteJson = anexosBeneficiarioMapper.toDto(anexosBeneficiario.get()).toJson();

//		auditoriaService.gerarAuditoria(anexosBeneficiario.get().getId().longValue(), anexosClienteJson,
//				Constants.EMPTY_STRING, 24L, "AnexosCliente", jwtTokenProvider.buscarIdUsuario(token));
	}

	@Override
	public AnexosBeneficiarioDTO buscarPorId(Long id) {

		Optional<AnexosBeneficiario> anexosBeneficiario = anexosBeneficiarioRepository.findById(id);

		if (anexosBeneficiario.isEmpty()) {
			throw new MsgGenericaPersonalizadaException("Anexo Beneficiário não existe ou foi excluído");
		}

		return anexosBeneficiarioMapper.toDto(anexosBeneficiario.get());
	}

	@Override
	public DownloadArquivoDTO buscarArquivoPorId(Long id) {
		Optional<AnexosBeneficiario> anexosBeneficiario = anexosBeneficiarioRepository.findById(id);
		DownloadArquivoDTO downloadArquivoDTO = new DownloadArquivoDTO();

		if (anexosBeneficiario.isEmpty()) {
			throw new MsgGenericaPersonalizadaException("Anexo Cliente não existe ou foi excluído");
		}

		downloadArquivoDTO.setData(Base64.encodeBase64String(anexosBeneficiario.get().getArquivo()));
		downloadArquivoDTO.setNomeArquivo(anexosBeneficiario.get().getNomeArquivo());
		return downloadArquivoDTO;
	}

	@Override
	public AnexosBeneficiarioWrapperDTO buscarPorCodigoBeneficiario(String idBeneficiario, Pageable pageable) {
		
		AnexosBeneficiarioWrapperDTO anexosBeneficiarioWrapper = new AnexosBeneficiarioWrapperDTO();
		anexosBeneficiarioWrapper
				.setListAnexosBeneficiarioDTO(anexosBeneficiarioRepository.buscarPorIdBeneficiario(Integer.valueOf(idBeneficiario), pageable));
		anexosBeneficiarioWrapper.setTotalRegistros(anexosBeneficiarioRepository.countByIdBeneficiario(Integer.valueOf(idBeneficiario)));
		
		BeneficiarioDTO beneficiario = this.buscarBeneficiario(Integer.valueOf(idBeneficiario));

		
		anexosBeneficiarioWrapper.setNomeBeneficiario(beneficiario.getNome());

		return anexosBeneficiarioWrapper;
	}

	@Override
	public AnexosArquivoBeneficiarioDTO salvarArquivo(MultipartFile arquivo) {
		AnexosBeneficiario anexosBeneficiario = new AnexosBeneficiario();

		try {
			this.validarExtensaoArquivo(arquivo.getOriginalFilename());
			this.validarTamanhoArquivo(arquivo.getBytes().length);
			anexosBeneficiario.setArquivo(arquivo.getBytes());
			anexosBeneficiario.setNomeArquivo(arquivo.getOriginalFilename());

			anexosBeneficiario = anexosBeneficiarioRepository.save(anexosBeneficiario);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}

		return anexosArquivoBeneficiarioMapper.toDto(anexosBeneficiario);
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

	private void validarTamanhoArquivo(int tamanhoArquivo) {
		int tamanhoArquivoMaximo = 1024 * 25000;
		if (tamanhoArquivo > tamanhoArquivoMaximo) {
			throw new MsgGenericaPersonalizadaException("Arquivo muito grande.");
		}
	}
	
	private BeneficiarioDTO buscarBeneficiario(Integer idBeneficiario) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		RestTemplate restTemplate = new RestTemplate();
		String url = urlMicroservico.getUrlImovel() + "/backend-imovel/beneficiario/pesquisar/" + idBeneficiario.toString();
		HttpEntity<?> request = new HttpEntity<>(headers);
		HttpEntity<BeneficiarioDTO> response = restTemplate.exchange(url, HttpMethod.GET, request, BeneficiarioDTO.class);
		return response.getBody();
	}

}
