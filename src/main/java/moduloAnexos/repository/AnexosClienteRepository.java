package moduloAnexos.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import moduloAnexos.model.AnexosCliente;
import moduloAnexos.service.dto.AnexosClienteDTO;

@Repository
public interface AnexosClienteRepository extends JpaRepository<AnexosCliente, Long> {

	@Query(value = "select new moduloAnexos.service.dto.AnexosClienteDTO(a.id,a.dataHoraInclusao,a.cdCliente,a.descricao,a.usuario,a.nomeArquivo) from AnexosCliente a where a.cdCliente =:cdCliente")
	List<AnexosClienteDTO> buscarPorCodigoCliente(@Param("cdCliente") Integer cdCliente,Pageable pageable);

	long countByCdCliente(Integer cdCliente);

}
