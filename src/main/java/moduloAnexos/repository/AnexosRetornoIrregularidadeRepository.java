package moduloAnexos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import moduloAnexos.model.AnexosRetornoIrregularidade;

@Repository
public interface AnexosRetornoIrregularidadeRepository extends JpaRepository<AnexosRetornoIrregularidade, Long> {

}
