package moduloAnexos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import moduloAnexos.model.IdRegraUsuarioAnexos;
import moduloAnexos.model.RegraUsuarioAnexos;

@Repository
public interface RegraUsuarioAnexosRepository extends JpaRepository<RegraUsuarioAnexos, IdRegraUsuarioAnexos> {

}
