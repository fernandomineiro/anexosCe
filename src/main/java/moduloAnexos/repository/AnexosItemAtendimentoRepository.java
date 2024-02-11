package moduloAnexos.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import moduloAnexos.model.AnexosItemAtendimento;
import moduloAnexos.service.dto.AnexosItemAtendimentoDTO;

@Repository
public interface AnexosItemAtendimentoRepository   extends JpaRepository<AnexosItemAtendimento, Long>{

	@Query(value = "select new moduloAnexos.service.dto.AnexosItemAtendimentoDTO(a.id,a.dataHoraInclusao,a.codAtendimento,a.refAtendimento,a.seqSS,a.descricao,a.usuario,a.nomeArquivo,a.tamanhoArquivo) from AnexosItemAtendimento a where a.codAtendimento = :cdAtendimento and a.refAtendimento = :refAtendimento and a.seqSS = :seqSS")
	List<AnexosItemAtendimentoDTO> buscarPorSS(@Param("cdAtendimento") Integer cdAtendimento,
			@Param("refAtendimento") Integer refAtendimento, @Param("seqSS") Short seqSS, Pageable pageable);
	
	@Query(value = "select new moduloAnexos.service.dto.AnexosItemAtendimentoDTO(a.tamanhoArquivo) from AnexosItemAtendimento a where a.codAtendimento = :cdAtendimento and a.refAtendimento = :refAtendimento and a.seqSS = :seqSS")
	List<AnexosItemAtendimentoDTO> buscarPorSS(@Param("cdAtendimento") Integer cdAtendimento,
			@Param("refAtendimento") Integer refAtendimento, @Param("seqSS") Short seqSS);
	
	long countByCodAtendimentoAndRefAtendimentoAndSeqSS(Integer cdAtendimento,Integer refAtendimento,Short seqSS);
}
