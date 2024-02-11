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
import moduloAnexos.model.AnexosCliente;
import moduloAnexos.repository.AnexosClienteRepository;
import moduloAnexos.service.AnexosClienteService;
import moduloAnexos.service.AuditoriaService;
import moduloAnexos.service.dto.AnexosArquivoClienteDTO;
import moduloAnexos.service.dto.AnexosClienteDTO;
import moduloAnexos.service.dto.AnexosClienteWrapperDTO;
import moduloAnexos.service.dto.ClienteDTO;
import moduloAnexos.service.dto.DownloadArquivoDTO;
import moduloAnexos.service.mapper.AnexosArquivoClienteMapper;
import moduloAnexos.service.mapper.AnexosClienteMapper;
import moduloAnexos.util.Constants;
import moduloAnexos.util.Formulas;
import moduloAnexos.util.UrlMicroservico;

@Service
@Transactional
public class AnexosClienteServiceImpl implements AnexosClienteService {

	@Autowired
	private AnexosClienteRepository anexosClienteRepository;
	
	@Autowired
	private AnexosClienteMapper anexosClienteMapper;

	@Autowired
	private AnexosArquivoClienteMapper anexosArquivoClienteMapper;
	
	@Autowired
	private UrlMicroservico urlMicroservico;
	
	@Autowired
	private Formulas formulas;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private AuditoriaService auditoriaService;

	@Override
	public AnexosClienteDTO salvar(AnexosClienteDTO anexosClienteDTO, String token) {

		ClienteDTO cliente = this.buscarCliente(anexosClienteDTO.getCdCliente());

		if (cliente == null) {
			throw new MsgGenericaPersonalizadaException("Cliente não existe.");
		}
		
		if (cliente.getMaint().equalsIgnoreCase("D")) {
			throw new MsgGenericaPersonalizadaException("Cliente foi excluído");
		}
		
		Optional<AnexosCliente> anexosClienteOp = anexosClienteRepository.findById(anexosClienteDTO.getId());

		if (anexosClienteOp.isEmpty()) {
			throw new MsgGenericaPersonalizadaException("Id não existe na base ou foi excluído");
		}

		AnexosCliente anexosCliente = anexosClienteOp.get();
		anexosCliente.setCdCliente(anexosClienteDTO.getCdCliente());
		anexosCliente.setDescricao(anexosClienteDTO.getDescricao());
		anexosCliente.setUsuario(anexosClienteDTO.getUsuario());
		anexosClienteRepository.save(anexosCliente);

		String anexosClienteJson = anexosClienteMapper.toDto(anexosCliente).toJson();

		auditoriaService.gerarAuditoria(anexosCliente.getId().longValue(), Constants.EMPTY_STRING, anexosClienteJson,
				24L, "AnexosCliente", jwtTokenProvider.buscarIdUsuario(token));

		return anexosClienteMapper.toDto(anexosCliente);
	}

	@Override
	public void excluir(Long id, String token) {
		Optional<AnexosCliente> anexosCliente = anexosClienteRepository.findById(id);

		if (anexosCliente.isEmpty()) {
			throw new MsgGenericaPersonalizadaException("Exclusão inválida");
		}

		ClienteDTO cliente = this.buscarCliente(anexosCliente.get().getCdCliente());

		if (cliente.getMaint().equalsIgnoreCase("D")) {
			throw new MsgGenericaPersonalizadaException("Cliente foi excluído");
		}
		
		anexosClienteRepository.delete(anexosCliente.get());

		String anexosClienteJson = anexosClienteMapper.toDto(anexosCliente.get()).toJson();

		auditoriaService.gerarAuditoria(anexosCliente.get().getId().longValue(), anexosClienteJson,
				Constants.EMPTY_STRING, 24L, "AnexosCliente", jwtTokenProvider.buscarIdUsuario(token));
	}

	@Override
	public AnexosClienteDTO buscarPorId(Long id) {

		Optional<AnexosCliente> anexosCliente = anexosClienteRepository.findById(id);

		if (anexosCliente.isEmpty()) {
			throw new MsgGenericaPersonalizadaException("Anexo Cliente não existe ou foi excluído");
		}

		return anexosClienteMapper.toDto(anexosCliente.get());
	}

	@Override
	public DownloadArquivoDTO buscarArquivoPorId(Long id) {
		Optional<AnexosCliente> anexosCliente = anexosClienteRepository.findById(id);
		DownloadArquivoDTO downloadArquivoDTO = new DownloadArquivoDTO();

		if (anexosCliente.isEmpty()) {
			throw new MsgGenericaPersonalizadaException("Anexo Cliente não existe ou foi excluído");
		}

		downloadArquivoDTO.setData(Base64.encodeBase64String(anexosCliente.get().getArquivo()));
		downloadArquivoDTO.setNomeArquivo(anexosCliente.get().getNomeArquivo());
		return downloadArquivoDTO;
	}

	@Override
	public AnexosClienteWrapperDTO buscarPorCodigoCliente(String cdCliente, Pageable pageable) {
		int dv = Integer.parseInt(cdCliente.substring(cdCliente.toString().length() - 1));
		cdCliente = cdCliente.substring(0, cdCliente.length() - 1);
		int dvCalculado = formulas.calculaDV(Integer.valueOf(cdCliente));
		
		if (dv != dvCalculado) {
			throw new MsgGenericaPersonalizadaException("DV inválido.");
		}
		
		AnexosClienteWrapperDTO anexosClienteWrapper = new AnexosClienteWrapperDTO();
		anexosClienteWrapper
				.setListAnexosClienteDTO(anexosClienteRepository.buscarPorCodigoCliente(Integer.valueOf(cdCliente), pageable));
		anexosClienteWrapper.setTotalRegistros(anexosClienteRepository.countByCdCliente(Integer.valueOf(cdCliente)));
		
		ClienteDTO cliente = this.buscarCliente(Integer.valueOf(cdCliente));
		
		if (cliente.getMaint().equalsIgnoreCase("D")) {
			anexosClienteWrapper.setClienteExcluido(1);
		}
		
		if (cliente.getClienteEspecial().equalsIgnoreCase("S")) anexosClienteWrapper.setClienteEspecial(1);
		
		if (cliente.getTipoCliente().getId() == 1  || cliente.getTipoCliente().getId() == 2 || cliente.getTipoCliente().getId() == 3)
			anexosClienteWrapper.setClientePublico(1);
		
		if (cliente.getValidado().equalsIgnoreCase("S")) anexosClienteWrapper.setClienteValidado(1);
		
		anexosClienteWrapper.setNomeCliente(cliente.getNome());

		return anexosClienteWrapper;
	}

	@Override
	public AnexosArquivoClienteDTO salvarArquivo(MultipartFile arquivo) {
		AnexosCliente anexosCliente = new AnexosCliente();

		try {
			this.validarExtensaoArquivo(arquivo.getOriginalFilename());
			this.validarTamanhoArquivo(arquivo.getBytes().length);
			anexosCliente.setArquivo(arquivo.getBytes());
			anexosCliente.setNomeArquivo(arquivo.getOriginalFilename());

			anexosCliente = anexosClienteRepository.save(anexosCliente);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}

		return anexosArquivoClienteMapper.toDto(anexosCliente);
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
		int tamanhoArquivoMaximo = 1024 * 10000;
		if (tamanhoArquivo > tamanhoArquivoMaximo) {
			throw new MsgGenericaPersonalizadaException("Arquivo muito grande.");
		}
	}
	
	private ClienteDTO buscarCliente(Integer codigoCliente) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		RestTemplate restTemplate = new RestTemplate();
		String url = urlMicroservico.getUrlCliente() + "/backend-cadastro/cliente/" + codigoCliente.toString() + "?semDV=1";
		HttpEntity<?> request = new HttpEntity<>(headers);
		HttpEntity<ClienteDTO> response = restTemplate.exchange(url, HttpMethod.GET, request, ClienteDTO.class);
		return response.getBody();
	}

}
