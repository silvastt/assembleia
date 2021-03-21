package br.com.assembleia.repository;

import br.com.assembleia.bo.Voto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("VotoRepository")
public interface VotoRepository extends MongoRepository<Voto, String> {
    List<Voto> findByIdPauta(String idPauta);
}
