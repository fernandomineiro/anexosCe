package moduloAnexos.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import moduloAnexos.model.AnexosImovel;
import moduloAnexos.service.dto.AnexosImovelDTO;

@Repository
public interface AnexosImovelRepository extends JpaRepository<AnexosImovel, Long> {

	@Query(value = "select new moduloAnexos.service.dto.AnexosImovelDTO(a.id,a.dataHoraInclusao,a.matriculaImovel,a.descricao,a.usuario,a.nomeArquivo) from AnexosImovel a where a.matriculaImovel =:matriculaImovel")
	List<AnexosImovelDTO> buscarPorMatriculaImovel(@Param("matriculaImovel") Integer matriculaImovel,Pageable pageable);

	long countByMatriculaImovel(Integer matriculaImovel);

}
