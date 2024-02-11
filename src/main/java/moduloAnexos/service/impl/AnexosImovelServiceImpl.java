package moduloAnexos.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import moduloAnexos.autenticacao.JwtTokenProvider;
import moduloAnexos.excecoes.MsgGenericaPersonalizadaException;
import moduloAnexos.model.AnexosImovel;
import moduloAnexos.model.IdRegraUsuarioAnexos;
import moduloAnexos.model.RegraUsuarioAnexos;
import moduloAnexos.repository.AnexosImovelRepository;
import moduloAnexos.repository.RegraUsuarioAnexosRepository;
import moduloAnexos.service.AnexosImovelService;
import moduloAnexos.service.AuditoriaService;
import moduloAnexos.service.dto.AnexosArquivoImovelDTO;
import moduloAnexos.service.dto.AnexosImovelDTO;
import moduloAnexos.service.dto.AnexosImovelWrapperDTO;
import moduloAnexos.service.dto.DownloadArquivoDTO;
import moduloAnexos.service.mapper.AnexosArquivoImovelMapper;
import moduloAnexos.service.mapper.AnexosImovelMapper;
import moduloAnexos.util.Constants;

@Service
@Transactional
public class AnexosImovelServiceImpl implements AnexosImovelService {

	@Autowired
	private AnexosImovelRepository anexosImovelRepository;

	@Autowired
	private RegraUsuarioAnexosRepository regraUsuarioAnexosRepository;

	@Autowired
	private AnexosImovelMapper anexosImovelMapper;

	@Autowired
	private AnexosArquivoImovelMapper anexosArquivoImovelMapper;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private AuditoriaService auditoriaService;

	@Override
	public AnexosImovelDTO salvar(AnexosImovelDTO anexosImovelDTO, String token) {

		Optional<AnexosImovel> anexosImovelOp = anexosImovelRepository.findById(anexosImovelDTO.getId());

		if (anexosImovelOp.isEmpty()) {
			throw new MsgGenericaPersonalizadaException("Id não existe na base ou foi excluído");
		}

		AnexosImovel anexosImovel = anexosImovelOp.get();
		anexosImovel.setMatriculaImovel(anexosImovelDTO.getMatriculaImovel());
		anexosImovel.setDescricao(anexosImovelDTO.getDescricao());
		anexosImovel.setUsuario(anexosImovelDTO.getUsuario());
		anexosImovelRepository.save(anexosImovel);

		String anexosImovelJson = anexosImovelMapper.toDto(anexosImovel).toJson();

		auditoriaService.gerarAuditoria(anexosImovel.getId().longValue(), Constants.EMPTY_STRING, anexosImovelJson, 23L,
				"AnexosImovel", jwtTokenProvider.buscarIdUsuario(token));

		return anexosImovelMapper.toDto(anexosImovel);

	}

	@Override
	public void excluir(Long id, String token) {

		if (!this.validarExclusao(token)) {
			throw new MsgGenericaPersonalizadaException("Não possui permissão para excluir anexos");
		}

		Optional<AnexosImovel> anexosImovel = anexosImovelRepository.findById(id);

		if (anexosImovel.isEmpty()) {

			throw new MsgGenericaPersonalizadaException("Exclusão inválida");
		}

		anexosImovelRepository.delete(anexosImovel.get());

		String anexosImovelJson = anexosImovelMapper.toDto(anexosImovel.get()).toJson();

		auditoriaService.gerarAuditoria(anexosImovel.get().getId().longValue(), anexosImovelJson,
				Constants.EMPTY_STRING, 23L, "AnexosImovel", jwtTokenProvider.buscarIdUsuario(token));

	}

	@Override
	public AnexosImovelDTO buscarPorId(Long id) {

		Optional<AnexosImovel> anexosImovel = anexosImovelRepository.findById(id);

		if (anexosImovel.isEmpty()) {

			throw new MsgGenericaPersonalizadaException("Anexo Imovel não existe ou foi excluído");
		}
		return anexosImovelMapper.toDto(anexosImovel.get());

	}

	@Override
	public DownloadArquivoDTO buscarArquivoPorId(Long id) {

		Optional<AnexosImovel> anexosImovel = anexosImovelRepository.findById(id);
		DownloadArquivoDTO downloadArquivoDTO = new DownloadArquivoDTO();
		

		if (anexosImovel.isEmpty()) {

			throw new MsgGenericaPersonalizadaException("Anexo Imovel não existe ou foi excluído");
		}
		downloadArquivoDTO.setData(Base64.encodeBase64String(anexosImovel.get().getArquivo()));
		downloadArquivoDTO.setNomeArquivo(anexosImovel.get().getNomeArquivo());
		return downloadArquivoDTO;

	}

	@Override
	public AnexosImovelWrapperDTO buscarPorMatriculaImovel(Integer matriculaImovel, Pageable pageable) {

		AnexosImovelWrapperDTO anexosImovelWrapper = new AnexosImovelWrapperDTO();
		anexosImovelWrapper
				.setListAnexosImovelDTO(anexosImovelRepository.buscarPorMatriculaImovel(matriculaImovel, pageable));
		anexosImovelWrapper.setTotalRegistros(anexosImovelRepository.countByMatriculaImovel(matriculaImovel));

		return anexosImovelWrapper;
	}

	@Override
	public AnexosArquivoImovelDTO salvarArquivo(MultipartFile arquivo) {
		AnexosImovel anexosImovel = new AnexosImovel();
		

		try {

			this.validarExtensaoArquivo(arquivo.getOriginalFilename(), arquivo);
			this.validarTamanhoArquivo(arquivo.getBytes().length);
			anexosImovel.setArquivo(arquivo.getBytes());
			anexosImovel.setNomeArquivo(arquivo.getOriginalFilename());

			anexosImovel = anexosImovelRepository.save(anexosImovel);

		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();

		}
		return anexosArquivoImovelMapper.toDto(anexosImovel);

	}

	private boolean validarExclusao(String token) {

		IdRegraUsuarioAnexos idRegraUsuarioAnexos = new IdRegraUsuarioAnexos();
		idRegraUsuarioAnexos.setIdUsuario(jwtTokenProvider.buscarIdUsuario(token).intValue());
		idRegraUsuarioAnexos.setIdregra(2);

		Optional<RegraUsuarioAnexos> regra = regraUsuarioAnexosRepository.findById(idRegraUsuarioAnexos);
		if (regra.isPresent()) {
			return true;
		}

		{
			return false;

		}

	}
	
	private void validarExtensaoArquivo(String nomeArquivo,MultipartFile arquivo) {
		int indice=nomeArquivo.lastIndexOf(".");
		String extensaoArquivo=nomeArquivo.substring(indice+1, nomeArquivo.length());
		if(extensaoArquivo.equalsIgnoreCase("zip"))
			this.abrirAquivoZip(arquivo);
		if(this.buscarExtensoesInvalidas().stream().anyMatch(e->e.equalsIgnoreCase(extensaoArquivo))) 
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
		int tamanhoArquivoMaximo=1024*10000;
		if(tamanhoArquivo>tamanhoArquivoMaximo) {
			throw new MsgGenericaPersonalizadaException("Arquivo muito grande.");
		}
		
	}
	
	private void abrirAquivoZip(MultipartFile arquivo) {
		try( ZipInputStream zipIn = new ZipInputStream(arquivo.getInputStream())){
			 ZipEntry entry;
			 while ((entry = zipIn.getNextEntry()) != null) {
				 int indice=entry.getName().lastIndexOf(".");
				 if(indice!=-1) {
					String extensaoArquivo=entry.getName().substring(indice+1, entry.getName().length());
					if(this.buscarExtensoesInvalidas().stream().anyMatch(e->e.equalsIgnoreCase(extensaoArquivo))) {
						throw new MsgGenericaPersonalizadaException("Arquivo inválido");
					}
				 }
			 }
			
		} catch (IOException e) {
			  throw new MsgGenericaPersonalizadaException("Erro ao salvar arquivo");
			
		}
	}
}

