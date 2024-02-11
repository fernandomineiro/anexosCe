package moduloAnexos.service.validacoes;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.beans.factory.annotation.Autowired;
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
import moduloAnexos.model.IdRegraUsuarioAnexos;
import moduloAnexos.model.RegraUsuarioAnexos;
import moduloAnexos.repository.RegraUsuarioAnexosRepository;
import moduloAnexos.service.dto.AnexosBaixarSolicitacaoServicoDTO;
import moduloAnexos.service.dto.BaixarSolicitacaoServicoDTO;
import moduloAnexos.service.dto.SolicitacaoServicoDTO;
import moduloAnexos.util.UrlMicroservico;

@Service
public class AnexosBaixarSolicitacaoServicoValidacoes {
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private RegraUsuarioAnexosRepository regraUsuarioAnexosRepository;
	@Autowired
	private UrlMicroservico urlMicroservico;

	public boolean validarExclusao(String token) {

		IdRegraUsuarioAnexos idRegraUsuarioAnexos = new IdRegraUsuarioAnexos();
		idRegraUsuarioAnexos.setIdUsuario(jwtTokenProvider.buscarIdUsuario(token).intValue());
		idRegraUsuarioAnexos.setIdregra(61);

		Optional<RegraUsuarioAnexos> regra = regraUsuarioAnexosRepository.findById(idRegraUsuarioAnexos);
		if (regra.isPresent()) 
			return true;
		else
			return false;

		

	}
	public void validarExtensaoArquivo(String nomeArquivo,byte[] arquivo) {
		int indice=nomeArquivo.lastIndexOf(".");
		String extensaoArquivo=nomeArquivo.substring(indice+1, nomeArquivo.length());
		if(extensaoArquivo.equalsIgnoreCase("zip"))
			this.abrirAquivoZip(arquivo);
		if(this.buscarExtensoesInvalidas().stream().anyMatch(e->e.equalsIgnoreCase(extensaoArquivo))) 
			throw new MsgGenericaPersonalizadaException("Arquivo inválido");
		}
	
	public List<String> buscarExtensoesInvalidas() {
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
		listExtensao.add("cmd");
		listExtensao.add("ws");
		listExtensao.add("scr");
		listExtensao.add("vbs");
		
		return listExtensao;
	}
	public void validarTamanhoArquivo(int tamanhoArquivo) {
		int tamanhoArquivoMaximo=1024*25000;
		if(tamanhoArquivo>tamanhoArquivoMaximo) {
			throw new MsgGenericaPersonalizadaException("Arquivo muito grande.");
		}
		
	}
	
	public void abrirAquivoZip(byte[] arquivo) {
		try( ZipInputStream zipIn = new ZipInputStream(new ByteArrayInputStream(arquivo))){
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
	public void validarTotalArquivo(int tamanhoArquivos) {
		int tamanhoArquivosMaximo=1024*1024*25*10;
		if(tamanhoArquivos>tamanhoArquivosMaximo)
			throw new ExcecaoRegraNegocio("Não foi possível adicionar este arquivo. Ultrapassou o limite  250MB");
	}
	
	public boolean validarSS(AnexosBaixarSolicitacaoServicoDTO anexosSolicitacaoServicoDTO) {
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
	
	public boolean validarSSJaBaixada(AnexosBaixarSolicitacaoServicoDTO anexosSolicitacaoServicoDTO) {
		String refAtendimento=anexosSolicitacaoServicoDTO.getRefAtendimento().toString().substring(4, 6)+"/"+anexosSolicitacaoServicoDTO.getRefAtendimento().toString().substring(2,4);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		RestTemplate restTemplate = new RestTemplate();
		String url = urlMicroservico.getUrlServico() + "/backend-servico/solicitacaoServico";
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("refAtendimento", refAtendimento)
				.queryParam("codAtendimento", anexosSolicitacaoServicoDTO.getCodAtendimento()).queryParam("ssSequencial", anexosSolicitacaoServicoDTO.getSeqSS());
		HttpEntity<?> request = new HttpEntity<>(headers);
		 try {
			HttpEntity<BaixarSolicitacaoServicoDTO> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, request,BaixarSolicitacaoServicoDTO.class);
			if(response.getBody().getDataBaixa() != 0) {
				 return true;
			}else {
				return false;
			}
		 }catch (HttpClientErrorException httpClientErrorException) {
		     throw new MsgGenericaPersonalizadaException("SS não existe");
		 }
	}

}
