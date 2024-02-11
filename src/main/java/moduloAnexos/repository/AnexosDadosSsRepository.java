package moduloAnexos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import moduloAnexos.model.AnexosDadosSs;
import moduloAnexos.service.dto.AnexosDadosSsDTO;

public interface AnexosDadosSsRepository  extends JpaRepository<AnexosDadosSs, Long> {
	
	@Query(value = "select new moduloAnexos.service.dto.AnexosDadosSsDTO(a.id,a.dataHoraInclusao,a.codAtendimento,a.refAtendimento,a.seqSS,a.descricao,a.usuario,a.nomeArquivo,a.tamanhoArquivo,a.acessoRestrito) "
			+ "from AnexosDadosSs a"
			+ " where a.codAtendimento = :cdAtendimento and a.refAtendimento = :refAtendimento and a.seqSS = :seqSS")
	List<AnexosDadosSsDTO> buscarPorSS(@Param("cdAtendimento") Integer cdAtendimento,
			@Param("refAtendimento") Integer refAtendimento, @Param("seqSS") Short seqSS);
	
	@Query(value = "select new moduloAnexos.service.dto.AnexosDadosSsDTO(a.id,a.dataHoraInclusao,a.codAtendimento,a.refAtendimento,a.seqSS,a.descricao,a.usuario,a.nomeArquivo,a.tamanhoArquivo,a.acessoRestrito) "
			+ "from AnexosDadosSs a"
			+ " where a.codAtendimento = :cdAtendimento and a.refAtendimento = :refAtendimento")
	List<AnexosDadosSsDTO> buscarPorSSAll(@Param("cdAtendimento") Integer cdAtendimento,
			@Param("refAtendimento") Integer refAtendimento);
	
	@Query(value = "select new moduloAnexos.service.dto.AnexosDadosSsDTO(a.tamanhoArquivo) "
			+ "from AnexosDadosSs a "
			+ "where a.codAtendimento = :cdAtendimento and a.refAtendimento = :refAtendimento and a.seqSS = :seqSS")
	List<AnexosDadosSsDTO> buscarPorSSResumido(@Param("cdAtendimento") Integer cdAtendimento,
			@Param("refAtendimento") Integer refAtendimento, @Param("seqSS") Short seqSS);

}
