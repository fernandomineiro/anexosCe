package moduloAnexos.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import moduloAnexos.model.AnexosFiscalizacao;
import moduloAnexos.service.dto.AnexosFiscalizacaoListDTO;
@Repository
public interface AnexosFiscalizacaoRepository extends JpaRepository<AnexosFiscalizacao, Long>{
	
	@Query(value = "select new moduloAnexos.service.dto.AnexosFiscalizacaoListDTO(a.id, a.idFiscalizacoesSS  , a.dataHoraInclusao  , a.cdAtendimento , a.refAtendimento  , a.seqSS  , a.descricao  , a.usuario  , a.nomeArquivo , a.arquivo  , a.tamanhoArquivo  , a.acessoRestrito) "
			+ "from AnexosFiscalizacao a "
			+ "where a.idFiscalizacoesSS = :idFiscalizacoesSS")
	List<AnexosFiscalizacaoListDTO> buscarPorFiscalizacao(@Param("idFiscalizacoesSS") Integer idFiscalizacoesSS);
	
}