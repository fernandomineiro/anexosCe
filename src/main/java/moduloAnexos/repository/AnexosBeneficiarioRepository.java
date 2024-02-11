package moduloAnexos.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import moduloAnexos.model.AnexosBeneficiario;
import moduloAnexos.service.dto.AnexosBeneficiarioDTO;

@Repository
public interface AnexosBeneficiarioRepository extends JpaRepository<AnexosBeneficiario, Long> {

	@Query(value = "select new moduloAnexos.service.dto.AnexosBeneficiarioDTO(a.id,a.dataHoraInclusao,a.idBeneficiario,a.descricao,a.usuario,a.nomeArquivo) from AnexosBeneficiario a where a.idBeneficiario =:idBeneficiario")
	List<AnexosBeneficiarioDTO> buscarPorIdBeneficiario(@Param("idBeneficiario") Integer idBeneficiario,Pageable pageable);

	long countByIdBeneficiario(Integer idBeneficiario);

}
