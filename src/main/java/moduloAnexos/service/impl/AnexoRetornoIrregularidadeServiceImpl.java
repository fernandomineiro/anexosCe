package moduloAnexos.service.impl;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import moduloAnexos.autenticacao.JwtTokenProvider;
import moduloAnexos.model.AnexosRetornoIrregularidade;
import moduloAnexos.repository.AnexosRetornoIrregularidadeRepository;
import moduloAnexos.service.AnexosIrregularidadeService;
import moduloAnexos.service.AuditoriaService;
import moduloAnexos.service.mapper.AnexosRetornoIrregularidadeMapper;
import moduloAnexos.util.Constants;
import moduloAnexos.util.ValidarArquivo;

@Service
@Transactional
public class AnexoRetornoIrregularidadeServiceImpl implements AnexosIrregularidadeService {
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private AuditoriaService auditoriaService;
	@Autowired
	private AnexosRetornoIrregularidadeRepository anexosRetornoIrregularidadeRepository;
	@Autowired
	private AnexosRetornoIrregularidadeMapper anexosRetornoIrregularidadeMapper;

	@Override
	public void salvar(List<MultipartFile> anexosIrregularidade, String token) {
		
		
		anexosIrregularidade.forEach(e -> this.salvar(e, token));
		
	}

	public void salvar(MultipartFile arquivo, String token) {
		String nome = arquivo.getOriginalFilename();
		String matricula;
		String dv;
		String referencia;
		String numPesquisa;
		String nomeArquivo;
		byte[] data;
		String result;
		
		matricula = nome.substring(0, nome.indexOf("_") - 1);
		dv = nome.substring(matricula.length(), nome.indexOf("_"));
		referencia = nome.substring(nome.indexOf("_") + 1, nome.indexOf("_", matricula.length() + 2));
		numPesquisa = nome.substring(((matricula.length() + 1) + referencia.length() + 2), nome.indexOf("_", (matricula.length() + 1) + referencia.length() + 2));
		nomeArquivo = nome.substring((matricula.length() + 1) + referencia.length() + numPesquisa.length() + 3);
		
		try {
			data = Base64.encodeBase64(arquivo.getBytes());
			result = new String(data);
			data = Base64.decodeBase64(result);
		} catch (IOException e) {
			data = null;
			e.printStackTrace();
		}
		
		ValidarArquivo.validarTotalArquivo(data.length);
		ValidarArquivo.validarExtensaoArquivo(nome, data);
		ValidarArquivo.validarTamanhoArquivo(data.length);
		
		AnexosRetornoIrregularidade anexoRetornoIrregularidade = new AnexosRetornoIrregularidade();
		anexoRetornoIrregularidade.setNomeArquivo(nomeArquivo);
		anexoRetornoIrregularidade.setArquivo(data);
		anexoRetornoIrregularidade.setMatricula(Integer.parseInt(matricula));
		anexoRetornoIrregularidade.setDv(Short.parseShort(dv));
		anexoRetornoIrregularidade.setReferencia(Integer.parseInt(referencia));
		anexoRetornoIrregularidade.setNumPesquisa(Integer.parseInt(numPesquisa));
		anexoRetornoIrregularidade.setTamanhoArquivo(data.length);
		anexoRetornoIrregularidade.setUsuario(jwtTokenProvider.buscarLogin(token));
		anexosRetornoIrregularidadeRepository.save(anexoRetornoIrregularidade);
		
		String anexosRetornoIrregularidadeJson = anexosRetornoIrregularidadeMapper.toDto(anexoRetornoIrregularidade).toJson();
		
		auditoriaService.gerarAuditoria(anexoRetornoIrregularidade.getId().longValue(), Constants.EMPTY_STRING, anexosRetornoIrregularidadeJson, 30L,
				"Anexos Irregularidade", jwtTokenProvider.buscarIdUsuario(token)); 
	}
}
