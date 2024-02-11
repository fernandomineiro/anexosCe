package moduloAnexos.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import moduloAnexos.autenticacao.JwtTokenProvider;
import moduloAnexos.excecoes.MsgGenericaPersonalizadaException;
import moduloAnexos.model.AnexosBaixarSolicitacaoServico;
import moduloAnexos.repository.AnexosBaixarSolicitacaoServicoRepository;
import moduloAnexos.service.AnexosBaixarSolicitacaoServicoService;
import moduloAnexos.service.AuditoriaService;
import moduloAnexos.service.dto.AnexosBaixarSolicitacaoServicoDTO;
import moduloAnexos.service.dto.AnexosBaixarSolicitacaoServicoWrapperDTO;
import moduloAnexos.service.dto.DownloadArquivoDTO;
import moduloAnexos.service.mapper.AnexosBaixarSolicitacaoServicoMapper;
import moduloAnexos.service.validacoes.AnexosBaixarSolicitacaoServicoValidacoes;
import moduloAnexos.util.Constants;
import moduloAnexos.util.ValidarArquivo;


@Service
@Transactional
public class AnexosBaixarSolicitacaoServicoServiceImpl implements AnexosBaixarSolicitacaoServicoService {
	
	@Autowired
	private AnexosBaixarSolicitacaoServicoRepository anexosBaixarSSRepository;
	@Autowired
	private AnexosBaixarSolicitacaoServicoValidacoes anexosBaixarSSValidacao;
	@Autowired
	private AnexosBaixarSolicitacaoServicoMapper anexosBaixarSSMapper;
	@Autowired
	private AuditoriaService auditoriaService;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Override
	public AnexosBaixarSolicitacaoServicoWrapperDTO buscarPorId(Integer cdAtendimento, String refAtendimentoFormatada,
															Short seqSS, Pageable pageable) {
		Integer refAtendimento = Integer.parseInt("20" + refAtendimentoFormatada.substring(3, 5) + refAtendimentoFormatada.substring(0, 2));
		AnexosBaixarSolicitacaoServicoWrapperDTO listAnexoBaixarSSWrapperDTO = new AnexosBaixarSolicitacaoServicoWrapperDTO();
		List<AnexosBaixarSolicitacaoServicoDTO> listAnexoBaixarSSDTO = this.anexosBaixarSSRepository.buscarPorSS(cdAtendimento, refAtendimento, seqSS, pageable);
		Integer totalArquivo = this.anexosBaixarSSRepository.buscarPorSS(cdAtendimento, refAtendimento, seqSS).stream().mapToInt(e -> e.getTamanhoArquivo()).sum();
		long totalRegistro = this.anexosBaixarSSRepository.countByCodAtendimentoAndRefAtendimentoAndSeqSS(cdAtendimento, refAtendimento, seqSS);
		
		listAnexoBaixarSSWrapperDTO.setListAnexosBaixarSolicitacaoServicoDTO(listAnexoBaixarSSDTO);
		listAnexoBaixarSSWrapperDTO.setTotalRegistro(totalRegistro);
		listAnexoBaixarSSWrapperDTO.setTotalArquivo(totalArquivo);
		
		return listAnexoBaixarSSWrapperDTO;
	}

	@Override
	public AnexosBaixarSolicitacaoServicoDTO salvar(AnexosBaixarSolicitacaoServicoDTO anexosBaixarSSDTO,String token) {
		if(!this.anexosBaixarSSValidacao.validarSS(anexosBaixarSSDTO)) {
			throw new MsgGenericaPersonalizadaException("Essa SS foi excluída.Não pode ser incluído um anexo");
		}
		
		AnexosBaixarSolicitacaoServico anexosBaixarSS = new AnexosBaixarSolicitacaoServico();
		byte[] arquivo= Base64.decodeBase64(anexosBaixarSSDTO.getData());

		Integer tamanhoArquivos = this.anexosBaixarSSRepository
				.buscarPorSS(anexosBaixarSSDTO.getCodAtendimento(), anexosBaixarSSDTO.getRefAtendimento(),
						anexosBaixarSSDTO.getSeqSS())
				.stream().mapToInt(e -> e.getTamanhoArquivo()).sum() + arquivo.length;

	    ValidarArquivo.validarTotalArquivo(tamanhoArquivos);
	    ValidarArquivo.validarExtensaoArquivo(anexosBaixarSSDTO.getNomeArquivo(), arquivo);
	    ValidarArquivo.validarTamanhoArquivo(arquivo.length);
		
		anexosBaixarSS.setArquivo(arquivo);
		anexosBaixarSS.setNomeArquivo(anexosBaixarSSDTO.getNomeArquivo());
		anexosBaixarSS.setTamanhoArquivo(arquivo.length);		
		anexosBaixarSS.setCodAtendimento(anexosBaixarSSDTO.getCodAtendimento());
		anexosBaixarSS.setRefAtendimento(anexosBaixarSSDTO.getRefAtendimento());
		anexosBaixarSS.setSeqSS(anexosBaixarSSDTO.getSeqSS());
		anexosBaixarSS.setDescricao(anexosBaixarSSDTO.getDescricao());
		anexosBaixarSS.setUsuario(anexosBaixarSSDTO.getUsuario());
		
		this.anexosBaixarSSRepository.save(anexosBaixarSS);

		String anexosSolicitacaoServicoJson = this.anexosBaixarSSMapper.toDto(anexosBaixarSS).toJson();

		auditoriaService.gerarAuditoria(anexosBaixarSS.getId().longValue(), Constants.EMPTY_STRING, anexosSolicitacaoServicoJson, 30L,
				"Anexos SS", jwtTokenProvider.buscarIdUsuario(token)); 	 
		return anexosBaixarSSMapper.toDto(anexosBaixarSS);
	}

	@Override
	public DownloadArquivoDTO buscarArquivoPorId(Long id) {
		Optional<AnexosBaixarSolicitacaoServico> anexosBaixarSS = this.anexosBaixarSSRepository.findById(id);
		DownloadArquivoDTO downloadArquivoDTO = new DownloadArquivoDTO();
		
		if (anexosBaixarSS.isEmpty()) {
			throw new MsgGenericaPersonalizadaException("Anexo Baixa SS não existe ou foi excluído");
		}
		downloadArquivoDTO.setData(Base64.encodeBase64String(anexosBaixarSS.get().getArquivo()));
		downloadArquivoDTO.setNomeArquivo(anexosBaixarSS.get().getNomeArquivo());
		return downloadArquivoDTO;
	}

	@Override
	public void excluir(Long id, String token) {
		Optional<AnexosBaixarSolicitacaoServico> anexosBaixaSS = this.anexosBaixarSSRepository.findById(id);
		
		if (!this.anexosBaixarSSValidacao.validarExclusao(token)) {
			throw new MsgGenericaPersonalizadaException("Não possui permissão para excluir anexos");
		}
		
		if (anexosBaixaSS.isEmpty()) {
			throw new MsgGenericaPersonalizadaException("Exclusão inválida");
		}

		if(!this.anexosBaixarSSValidacao.validarSS(this.anexosBaixarSSMapper.toDto(anexosBaixaSS.get()))) {
			 throw new MsgGenericaPersonalizadaException("Essa baixa SS foi excluída. Não pode ser excluído um anexo");
		}
		
		if(this.anexosBaixarSSValidacao.validarSSJaBaixada(this.anexosBaixarSSMapper.toDto(anexosBaixaSS.get()))) {
			 throw new MsgGenericaPersonalizadaException("Não é possível excluir arquivo de SS baixada");
		}
			
		this.anexosBaixarSSRepository.delete(anexosBaixaSS.get());

		String anexosSolicitacaoServicoJson = this.anexosBaixarSSMapper.toDto(anexosBaixaSS.get()).toJson();

		auditoriaService.gerarAuditoria(anexosBaixaSS.get().getId().longValue(), anexosSolicitacaoServicoJson,
				Constants.EMPTY_STRING, 30L, "Anexos SS", jwtTokenProvider.buscarIdUsuario(token));
	}
	
	@Override
	public void excluirPorSs(Integer cdAtendimento, Integer refAtendimento, Short seqSS, Integer idSs, String token) {
		List<AnexosBaixarSolicitacaoServicoDTO> listAnexosBaixarSsDTO = anexosBaixarSSRepository
				.buscarPorSsSemPaginacao(cdAtendimento, refAtendimento, seqSS);

		for (AnexosBaixarSolicitacaoServicoDTO anexosBaixarSsDTO : listAnexosBaixarSsDTO) {
			String anexosSsJson = anexosBaixarSsDTO.toJson();
			
			anexosBaixarSSRepository.deleteById(anexosBaixarSsDTO.getId());

			auditoriaService.gerarAuditoria(idSs.longValue(), anexosSsJson, Constants.EMPTY_STRING, 30L, "Dados SS",
					jwtTokenProvider.buscarIdUsuario(token));
		}
	}

	@Override
	public List<AnexosBaixarSolicitacaoServicoDTO> buscarPorId(Integer cdAtendimento, String refAtendimentoFormatada,
			Short seqSS) {
		Integer refAtendimento = Integer
				.parseInt("20" + refAtendimentoFormatada.substring(3, 5) + refAtendimentoFormatada.substring(0, 2));
		
		return anexosBaixarSSRepository.buscarPorSsSemPaginacao(cdAtendimento, refAtendimento, seqSS);
	}
	
	@Override
	public List<AnexosBaixarSolicitacaoServicoDTO> buscarPorIdAll(Integer cdAtendimento, String refAtendimentoFormatada,
			Short seqSS) {
		Integer refAtendimento = Integer
				.parseInt("20" + refAtendimentoFormatada.substring(3, 5) + refAtendimentoFormatada.substring(0, 2));
		
		return anexosBaixarSSRepository.buscarPorSsSemPaginacaoAll(cdAtendimento, refAtendimento);
	}

	
	
}
