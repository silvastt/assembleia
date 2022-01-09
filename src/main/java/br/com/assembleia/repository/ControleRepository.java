package br.com.assembleia.repository;

import br.com.assembleia.bo.Controle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("ControleRepository")
public interface ControleRepository extends MongoRepository<Controle, String> {
    Optional<Controle> findByIdAssociadoAndAndIdPauta(String idAssociado, String idPauta);
}
