package br.com.assembleia.repository;

import br.com.assembleia.bo.Pauta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository("PautaRepository")
public interface PautaRepository extends MongoRepository<Pauta, String> {
  @Query("{'sessao.aberta': true}")
  List<Pauta> findByPautasAbertas();
}
