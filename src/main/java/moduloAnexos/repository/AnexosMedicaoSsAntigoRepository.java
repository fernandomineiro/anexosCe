package moduloAnexos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import moduloAnexos.model.AnexosMedicaoSsAntigo;
import moduloAnexos.service.dto.AnexosMedicaoSsDTO;

@Repository
public interface AnexosMedicaoSsAntigoRepository extends JpaRepository<AnexosMedicaoSsAntigo, Long> {

	@Query(value = "select new moduloAnexos.service.dto.AnexosMedicaoSsDTO(a.id,a.dataHoraInclusao,a.descricao,a.usuario,a.nomeArquivo,a.acessoRestrito) "
			+ "from AnexosMedicaoSsAntigo a "
			+ "where a.idSolicitacaoServico = :idMedicao")
	List<AnexosMedicaoSsDTO> buscarPorSS(@Param("idMedicao") Long idMedicao);
}
