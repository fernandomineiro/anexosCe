package moduloAnexos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import moduloAnexos.model.AnexosMedicaoSs;
import moduloAnexos.service.dto.AnexosMedicaoSsDTO;

@Repository
public interface AnexosMedicaoSsRepository extends JpaRepository<AnexosMedicaoSs, Long>{

	@Query(value = "select new moduloAnexos.service.dto.AnexosMedicaoSsDTO(a.tamanhoArquivo) "
			+ "from AnexosMedicaoSs a "
			+ "where a.codAtendimento = :cdAtendimento and a.refAtendimento = :refAtendimento and a.seqSS = :seqSS")
	List<AnexosMedicaoSsDTO> buscarPorSSResumido(@Param("cdAtendimento") Integer cdAtendimento,
			@Param("refAtendimento") Integer refAtendimento, @Param("seqSS") Short seqSS);

	@Query(value = "select new moduloAnexos.service.dto.AnexosMedicaoSsDTO(a.id,a.dataHoraInclusao,a.codAtendimento,a.refAtendimento,a.seqSS,a.descricao,a.usuario,a.nomeArquivo,a.tamanhoArquivo,a.acessoRestrito) "
			+ "from AnexosMedicaoSs a"
			+ " where a.codAtendimento = :cdAtendimento and a.refAtendimento = :refAtendimento and a.seqSS = :seqSS")
	List<AnexosMedicaoSsDTO> buscarPorSS(@Param("cdAtendimento") Integer cdAtendimento,
			@Param("refAtendimento") Integer refAtendimento, @Param("seqSS") Short seqSS);
	
	@Query(value = "select new moduloAnexos.service.dto.AnexosMedicaoSsDTO(a.id,a.dataHoraInclusao,a.codAtendimento,a.refAtendimento,a.seqSS,a.descricao,a.usuario,a.nomeArquivo,a.tamanhoArquivo,a.acessoRestrito) "
			+ "from AnexosMedicaoSs a"
			+ " where a.codAtendimento = :cdAtendimento and a.refAtendimento = :refAtendimento")
	List<AnexosMedicaoSsDTO> buscarPorSSAll(@Param("cdAtendimento") Integer cdAtendimento,
			@Param("refAtendimento") Integer refAtendimento);
	
}
