package moduloAnexos.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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
import moduloAnexos.excecoes.ExcecaoRegraNegocio;
import moduloAnexos.excecoes.MsgGenericaPersonalizadaException;
import moduloAnexos.model.AnexosItemAtendimento;
import moduloAnexos.model.IdRegraUsuarioAnexos;
import moduloAnexos.model.RegraUsuarioAnexos;
import moduloAnexos.repository.AnexosItemAtendimentoRepository;
import moduloAnexos.repository.RegraUsuarioAnexosRepository;
import moduloAnexos.service.AnexosItemAtendimentoService;
import moduloAnexos.service.AuditoriaService;
import moduloAnexos.service.dto.AnexosItemAtendimentoDTO;
import moduloAnexos.service.dto.AnexosItemAtendimentoWrapperDTO;
import moduloAnexos.service.dto.DownloadArquivoDTO;
import moduloAnexos.service.dto.ItemAtendimentoDTO;
import moduloAnexos.service.mapper.AnexosItemAtendimentoMapper;
import moduloAnexos.util.Constants;
import moduloAnexos.util.UrlMicroservico;

@Service
@Transactional
public class AnexosItemAtendimentoServiceImpl implements AnexosItemAtendimentoService {

	@Autowired
	private AnexosItemAtendimentoRepository anexosItemAtendimentoRepository;
	@Autowired
	private RegraUsuarioAnexosRepository regraUsuarioAnexosRepository;
	@Autowired
	private AnexosItemAtendimentoMapper anexosItemAtendimentoMapper;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private AuditoriaService auditoriaService;

	@Autowired
	private UrlMicroservico urlMicroservico;

	@Override
	public AnexosItemAtendimentoDTO salvar(AnexosItemAtendimentoDTO anexosItemAtendimentoDTO, String token) {

		if (!this.validarItemAtendimento(anexosItemAtendimentoDTO))
			throw new MsgGenericaPersonalizadaException(
					"Esse itemAtendimento foi excluído.Não pode ser incluído um anexo");
		AnexosItemAtendimento anexosItemAtendimento = new AnexosItemAtendimento();
		byte[] arquivo = Base64.decodeBase64(anexosItemAtendimentoDTO.getData());

		Integer tamanhoArquivos = anexosItemAtendimentoRepository
				.buscarPorSS(anexosItemAtendimentoDTO.getCodAtendimento(), anexosItemAtendimentoDTO.getRefAtendimento(),
						anexosItemAtendimentoDTO.getSeqSS())
				.stream().mapToInt(e -> e.getTamanhoArquivo()).sum() + arquivo.length;

		this.validarTotalArquivo(tamanhoArquivos);
		this.validarExtensaoArquivo(anexosItemAtendimentoDTO.getNomeArquivo(), arquivo);
		this.validarTamanhoArquivo(arquivo.length);

		anexosItemAtendimento.setArquivo(arquivo);
		anexosItemAtendimento.setNomeArquivo(anexosItemAtendimentoDTO.getNomeArquivo());
		anexosItemAtendimento.setTamanhoArquivo(arquivo.length);
		anexosItemAtendimento.setCodAtendimento(anexosItemAtendimentoDTO.getCodAtendimento());
		anexosItemAtendimento.setRefAtendimento(anexosItemAtendimentoDTO.getRefAtendimento());
		anexosItemAtendimento.setSeqSS(anexosItemAtendimentoDTO.getSeqSS());
		anexosItemAtendimento.setDescricao(anexosItemAtendimentoDTO.getDescricao());
		anexosItemAtendimento.setUsuario(jwtTokenProvider.buscarLogin(token));
		anexosItemAtendimentoRepository.save(anexosItemAtendimento);

		String anexosItemAtendimentoJson = anexosItemAtendimentoMapper.toDto(anexosItemAtendimento).toJson();

		auditoriaService.gerarAuditoria(anexosItemAtendimento.getId().longValue(), Constants.EMPTY_STRING,
				anexosItemAtendimentoJson, 33L, "Anexos Item Atendimento", jwtTokenProvider.buscarIdUsuario(token));

		return anexosItemAtendimentoMapper.toDto(anexosItemAtendimento);
	}

	@Override
	public void excluir(Long id, String token) {
		if (!this.validarExclusao(token)) {
			throw new MsgGenericaPersonalizadaException("Não possui permissão para excluir anexos");
		}

		Optional<AnexosItemAtendimento> anexosItemAtendimento = anexosItemAtendimentoRepository.findById(id);

		if (anexosItemAtendimento.isEmpty()) {

			throw new MsgGenericaPersonalizadaException("Exclusão inválida");
		}

		anexosItemAtendimentoRepository.delete(anexosItemAtendimento.get());

		String anexosItemAtendimentoJson = anexosItemAtendimentoMapper.toDto(anexosItemAtendimento.get()).toJson();

		auditoriaService.gerarAuditoria(anexosItemAtendimento.get().getId().longValue(), anexosItemAtendimentoJson,
				Constants.EMPTY_STRING, 33L, "Anexos Item Atendimento", jwtTokenProvider.buscarIdUsuario(token));

	}

	@Override
	public AnexosItemAtendimentoDTO buscarPorId(Long id) {
		Optional<AnexosItemAtendimento> anexosItemAtendimento = anexosItemAtendimentoRepository.findById(id);

		if (anexosItemAtendimento.isEmpty()) {

			throw new MsgGenericaPersonalizadaException("Anexo item atendimento não existe ou foi excluído");
		}
		return anexosItemAtendimentoMapper.toDto(anexosItemAtendimento.get());

	}

	@Override
	public AnexosItemAtendimentoWrapperDTO buscarPorId(Integer cdAtendimento, String refAtendimentoFormatada,
			Short seqAtendimento, Pageable pageable) {
		Integer refAtendimento = Integer
				.parseInt("20" + refAtendimentoFormatada.substring(3, 5) + refAtendimentoFormatada.substring(0, 2));
		AnexosItemAtendimentoWrapperDTO listAnexoItemAtendimentoDTO = new AnexosItemAtendimentoWrapperDTO();
		listAnexoItemAtendimentoDTO.setListAnexosItemAtendimentoDTO(
				anexosItemAtendimentoRepository.buscarPorSS(cdAtendimento, refAtendimento, seqAtendimento, pageable));
		listAnexoItemAtendimentoDTO.setTotalRegistro(anexosItemAtendimentoRepository
				.countByCodAtendimentoAndRefAtendimentoAndSeqSS(cdAtendimento, refAtendimento, seqAtendimento));
		listAnexoItemAtendimentoDTO.setTotalArquivo(
				anexosItemAtendimentoRepository.buscarPorSS(cdAtendimento, refAtendimento, seqAtendimento).stream()
						.mapToInt(e -> e.getTamanhoArquivo()).sum());
		return listAnexoItemAtendimentoDTO;
	}

	@Override
	public DownloadArquivoDTO buscarArquivoPorId(Long id) {
		Optional<AnexosItemAtendimento> anexosItemAtendimento = anexosItemAtendimentoRepository.findById(id);
		DownloadArquivoDTO downloadArquivoDTO = new DownloadArquivoDTO();

		if (anexosItemAtendimento.isEmpty()) {

			throw new MsgGenericaPersonalizadaException("Anexo de item atendimento  não existe ou foi excluído");
		}
		downloadArquivoDTO.setData(Base64.encodeBase64String(anexosItemAtendimento.get().getArquivo()));
		downloadArquivoDTO.setNomeArquivo(anexosItemAtendimento.get().getNomeArquivo());
		return downloadArquivoDTO;
	}

	private boolean validarExclusao(String token) {

		IdRegraUsuarioAnexos idRegraUsuarioAnexos = new IdRegraUsuarioAnexos();
		idRegraUsuarioAnexos.setIdUsuario(jwtTokenProvider.buscarIdUsuario(token).intValue());
		idRegraUsuarioAnexos.setIdregra(54);

		Optional<RegraUsuarioAnexos> regra = regraUsuarioAnexosRepository.findById(idRegraUsuarioAnexos);
		if (regra.isPresent())
			return true;
		else
			return false;

	}

	private void validarExtensaoArquivo(String nomeArquivo, byte[] arquivo) {
		int indice = nomeArquivo.lastIndexOf(".");
		String extensaoArquivo = nomeArquivo.substring(indice + 1, nomeArquivo.length());
		if (extensaoArquivo.equalsIgnoreCase("zip"))
			this.abrirAquivoZip(arquivo);
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
		listExtensao.add("msi");

		return listExtensao;
	}

	private void validarTamanhoArquivo(int tamanhoArquivo) {
		int tamanhoArquivoMaximo = 15 * 1000000;
		if (tamanhoArquivo > tamanhoArquivoMaximo) {
			throw new MsgGenericaPersonalizadaException("Arquivo muito grande.");
		}

	}

	private void abrirAquivoZip(byte[] arquivo) {
		try (ZipInputStream zipIn = new ZipInputStream(new ByteArrayInputStream(arquivo))) {
			ZipEntry entry;
			while ((entry = zipIn.getNextEntry()) != null) {
				int indice = entry.getName().lastIndexOf(".");
				if (indice != -1) {
					String extensaoArquivo = entry.getName().substring(indice + 1, entry.getName().length());
					if (this.buscarExtensoesInvalidas().stream().anyMatch(e -> e.equalsIgnoreCase(extensaoArquivo))) {
						throw new MsgGenericaPersonalizadaException("Arquivo inválido");
					}
				}
			}

		} catch (IOException e) {
			throw new MsgGenericaPersonalizadaException("Erro ao salvar arquivo");

		}
	}

	private void validarTotalArquivo(int tamanhoArquivos) {
		int tamanhoArquivosMaximo = 1024 * 1024 * 15;
		if (tamanhoArquivos > tamanhoArquivosMaximo)
			throw new ExcecaoRegraNegocio("Não foi possível adicionar este arquivo. Ultrapassou o limite  15MB");
	}

	private boolean validarItemAtendimento(AnexosItemAtendimentoDTO anexosItemAtendimentoDTO) {
		String refAtendimento = anexosItemAtendimentoDTO.getRefAtendimento().toString().substring(4, 6) + "/"
				+ anexosItemAtendimentoDTO.getRefAtendimento().toString().substring(2, 4);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		RestTemplate restTemplate = new RestTemplate();
		String url = urlMicroservico.getUrlServico() + "/backend-servico/itemAtendimento/buscaItemAtendimento";
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
				.queryParam("refAtendimento", refAtendimento)
				.queryParam("codAtendimento", anexosItemAtendimentoDTO.getCodAtendimento())
				.queryParam("seqAtendimento", anexosItemAtendimentoDTO.getSeqSS());
		HttpEntity<?> request = new HttpEntity<>(headers);
		try {
			HttpEntity<ItemAtendimentoDTO> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET,
					request, ItemAtendimentoDTO.class);
			if (response.getBody().isFlagExclusao())
				return false;
			else
				return true;
		} catch (HttpClientErrorException httpClientErrorException) {
			throw new MsgGenericaPersonalizadaException("ItemAtendimento não existe");
		}

	}
}
