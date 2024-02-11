package moduloAnexos.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import moduloAnexos.model.AnexosBaixarSolicitacaoServico;
import moduloAnexos.service.dto.AnexosBaixarSolicitacaoServicoDTO;
@Repository
public interface AnexosBaixarSolicitacaoServicoRepository extends JpaRepository<AnexosBaixarSolicitacaoServico, Long> {
	
	@Query(value = "select new moduloAnexos.service.dto.AnexosBaixarSolicitacaoServicoDTO(a.id,a.dataHoraInclusao,a.codAtendimento,a.refAtendimento,a.seqSS,a.descricao,a.usuario,a.nomeArquivo,a.tamanhoArquivo) from AnexosBaixarSolicitacaoServico a where a.codAtendimento = :cdAtendimento and a.refAtendimento = :refAtendimento and a.seqSS = :seqSS")
	List<AnexosBaixarSolicitacaoServicoDTO> buscarPorSS(@Param("cdAtendimento") Integer cdAtendimento,
			@Param("refAtendimento") Integer refAtendimento, @Param("seqSS") Short seqSS, Pageable pageable);
	
	@Query(value = "select new moduloAnexos.service.dto.AnexosBaixarSolicitacaoServicoDTO(a.tamanhoArquivo) from AnexosBaixarSolicitacaoServico a where a.codAtendimento = :cdAtendimento and a.refAtendimento = :refAtendimento and a.seqSS = :seqSS")
	List<AnexosBaixarSolicitacaoServicoDTO> buscarPorSS(@Param("cdAtendimento") Integer cdAtendimento,
			@Param("refAtendimento") Integer refAtendimento, @Param("seqSS") Short seqSS);
	
	@Query(value = "select new moduloAnexos.service.dto.AnexosBaixarSolicitacaoServicoDTO(a.id,a.dataHoraInclusao,a.codAtendimento,a.refAtendimento,a.seqSS,a.descricao,a.usuario,a.nomeArquivo,a.tamanhoArquivo) from AnexosBaixarSolicitacaoServico a where a.codAtendimento = :cdAtendimento and a.refAtendimento = :refAtendimento and a.seqSS = :seqSS")
	List<AnexosBaixarSolicitacaoServicoDTO> buscarPorSsSemPaginacao(@Param("cdAtendimento") Integer cdAtendimento,
			@Param("refAtendimento") Integer refAtendimento, @Param("seqSS") Short seqSS);
	
	@Query(value = "select new moduloAnexos.service.dto.AnexosBaixarSolicitacaoServicoDTO(a.id,a.dataHoraInclusao,a.codAtendimento,a.refAtendimento,a.seqSS,a.descricao,a.usuario,a.nomeArquivo,a.tamanhoArquivo) from AnexosBaixarSolicitacaoServico a where a.codAtendimento = :cdAtendimento and a.refAtendimento = :refAtendimento")
	List<AnexosBaixarSolicitacaoServicoDTO> buscarPorSsSemPaginacaoAll(@Param("cdAtendimento") Integer cdAtendimento,
			@Param("refAtendimento") Integer refAtendimento);
	
	long countByCodAtendimentoAndRefAtendimentoAndSeqSS(Integer cdAtendimento,Integer refAtendimento,Short seqSS);
}
