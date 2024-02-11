package moduloAnexos.service.impl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import moduloAnexos.comparator.AnexoFiscalizacaoDTOComparator;
import moduloAnexos.excecoes.MsgGenericaPersonalizadaException;
import moduloAnexos.model.AnexosFiscalizacao;
import moduloAnexos.repository.AnexosFiscalizacaoRepository;
import moduloAnexos.service.AnexosFiscalizacaoService;
import moduloAnexos.service.dto.AnexosFiscalizacaoDTO;
import moduloAnexos.service.dto.AnexosFiscalizacaoListDTO;
import moduloAnexos.service.dto.AnexosFiscalizacaoWrapperDTO;
import moduloAnexos.service.dto.DownloadArquivoDTO;
import moduloAnexos.service.mapper.AnexosFiscalizacaoMapper;
import moduloAnexos.util.ValidarArquivo;

@Service
@Transactional
public class AnexosFiscalizacaoServiceImpl implements AnexosFiscalizacaoService {

	@Autowired
	private AnexosFiscalizacaoRepository anexosFiscalizacaoRepository;
	
	@Autowired
	AnexosFiscalizacaoMapper fiscalizacaoMapper;

	
	@Override
	public AnexosFiscalizacaoDTO salvar(AnexosFiscalizacaoDTO fiscalizacao, String token) {

	        AnexosFiscalizacao anexosFiscalizacao = new AnexosFiscalizacao();
		  
		    byte[] arquivo= Base64.decodeBase64(fiscalizacao.getData());
		
		    ValidarArquivo.validarExtensaoArquivo(fiscalizacao.getNomeArquivo(), arquivo);
		    ValidarArquivo.validarTamanhoArquivo(arquivo.length);
			LocalDateTime horaDaInclusao = LocalDateTime.now();
			
		    anexosFiscalizacao.setArquivo(arquivo);
		    anexosFiscalizacao.setNomeArquivo(fiscalizacao.getNomeArquivo());
		    anexosFiscalizacao.setTamanhoArquivo(arquivo.length);		
		    anexosFiscalizacao.setCdAtendimento(fiscalizacao.getCdAtendimento());
		    anexosFiscalizacao.setRefAtendimento(Integer.parseInt(fiscalizacao.getRefAtendimento()));
		    anexosFiscalizacao.setSeqSS(fiscalizacao.getSeqSS());
		    anexosFiscalizacao.setDescricao(fiscalizacao.getDescricao());
		    anexosFiscalizacao.setUsuario(fiscalizacao.getUsuario());
		    anexosFiscalizacao.setAcessoRestrito(fiscalizacao.getAcessoRestrito());
		    anexosFiscalizacao.setIdFiscalizacoesSS(fiscalizacao.getIdFiscalizacoesSS());
		    anexosFiscalizacao.setDataHoraInclusao(horaDaInclusao);
		    
		    anexosFiscalizacaoRepository.save(anexosFiscalizacao);

		    return fiscalizacaoMapper.toDto(anexosFiscalizacao);
	}
	
	
	@Override
	public AnexosFiscalizacaoWrapperDTO buscarPorIdSs(Integer idFiscalizacoesSS, Pageable pageable) {
		
		AnexosFiscalizacaoWrapperDTO fiscalizacaoWrapperDTO = new AnexosFiscalizacaoWrapperDTO();
		
		
		List<AnexosFiscalizacaoListDTO> list = anexosFiscalizacaoRepository.buscarPorFiscalizacao(idFiscalizacoesSS);
		
		fiscalizacaoWrapperDTO.setTotalRegistro(list.size());
		String ordenarCampo = "";
		String ordem = "";
		if (pageable.getSort() != null && pageable.getSort().iterator().hasNext()) {
			Order order = pageable.getSort().iterator().next();
			ordenarCampo = order.getProperty();
			ordem = (order.isAscending() ? "asc" : "desc");
		}
		
		Collections.sort(list, new AnexoFiscalizacaoDTOComparator(ordenarCampo, ordem));
		int indice = pageable.getPageSize() * pageable.getPageNumber();
		fiscalizacaoWrapperDTO.setListAnexosSolicitacaoServicoDTO(list.stream().skip(indice).limit(pageable.getPageSize()).collect(Collectors.toList()));
		
			return fiscalizacaoWrapperDTO;
	}
	
	@Override
	public DownloadArquivoDTO buscarArquivoPorId(Long id) {
	
		DownloadArquivoDTO downloadArquivoDTO = new DownloadArquivoDTO();
		
		AnexosFiscalizacaoDTO anexo = this.buscaArquivoPorIdNovoOuAntigo(id);

		downloadArquivoDTO.setData(Base64.encodeBase64String(anexo.getArquivo()));
		downloadArquivoDTO.setNomeArquivo(anexo.getNomeArquivo());
		return downloadArquivoDTO;
		
	}
	
	private AnexosFiscalizacaoDTO buscaArquivoPorIdNovoOuAntigo(Long id) {
		return fiscalizacaoMapper.toDto(anexosFiscalizacaoRepository.findById(id).get());
	}
	
	@Override
	public void excluir(Long id, String token) {
		AnexosFiscalizacao anexo = this.validarExclusao(anexosFiscalizacaoRepository.findById(id));
	    anexosFiscalizacaoRepository.delete(anexo);
	}
	
	private <T> T  validarExclusao(Optional<T> anexo) {
		if(anexo.isEmpty())
      	  throw new MsgGenericaPersonalizadaException("Exclusão inválida");
		else
		 return anexo.get();	
	}

	@Override
	public void excluirTodosPorFiscalizacao(Integer idFiscalizacoesSS) {
		List<AnexosFiscalizacaoListDTO> list = anexosFiscalizacaoRepository.buscarPorFiscalizacao(idFiscalizacoesSS);
		for(AnexosFiscalizacaoListDTO fiscalizacao:list) {
			anexosFiscalizacaoRepository.deleteById(fiscalizacao.getId());
			System.out.println(fiscalizacao.getId());
		}
	}

	
}


