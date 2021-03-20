package br.com.assembleia.repository;

import br.com.assembleia.bo.Pauta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository("VotoRepository")
public interface VotoRepository extends MongoRepository<Pauta, String> {
}
