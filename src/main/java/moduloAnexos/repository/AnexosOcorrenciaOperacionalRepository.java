package moduloAnexos.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import moduloAnexos.model.AnexosOcorrenciaOperacional;
import moduloAnexos.service.dto.AnexosOcorrenciaOperacionalDTO;

@Repository
public interface AnexosOcorrenciaOperacionalRepository extends JpaRepository<AnexosOcorrenciaOperacional, Long> {

	@Query(value = "select new moduloAnexos.service.dto.AnexosOcorrenciaOperacionalDTO("
			+ "a.id,a.dataHoraInclusao,a.idOcorrenciaOperacional,a.descricao,a.usuario,a.nomeArquivo) "
			+ "from AnexosOcorrenciaOperacional a "
			+ "where a.idOcorrenciaOperacional =:idOcorrenciaOperacional ")
	List<AnexosOcorrenciaOperacionalDTO> buscarPorId(Long idOcorrenciaOperacional, Pageable pageable);

	long countByIdOcorrenciaOperacional(Long idOcorrenciaOperacional);
	
	List<AnexosOcorrenciaOperacional> findAllByIdOcorrenciaOperacional(Long idOcorrenciaOperacional);

}
