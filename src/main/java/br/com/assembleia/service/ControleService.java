package br.com.assembleia.service;

import br.com.assembleia.bo.Controle;
import br.com.assembleia.error.ErroInternoException;
import br.com.assembleia.repository.ControleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static br.com.assembleia.util.MessagesProperties.ERRO_REGISTRAR_CONTROLE;

@Service("ControleService")
public class ControleService {

    Logger logger = LoggerFactory.getLogger(ControleService.class);

    private final ControleRepository repository;

    public ControleService(final ControleRepository repository) {
        this.repository = repository;
    }

    public void registrar(final Controle controle) {
        try {
            logger.info("Salvando controle de voto: [Pauta] " + controle.getIdPauta() + " [Associado] " + controle.getIdAssociado());
            repository.save(controle);
        } catch (Exception e) {
            logger.error("Erro ao registrar controle: [Pauta] " + controle.getIdPauta() + " [Associado] " + controle.getIdAssociado());
            throw new ErroInternoException(ERRO_REGISTRAR_CONTROLE);
        }
    }

    public Optional<Controle> buscarControle(final String idAssociado, final String idPauta) {
        logger.info("Buscando dados de controle: [Pauta] " + idPauta + " [Associado] " + idAssociado);
        return repository.findByIdAssociadoAndAndIdPauta(idAssociado, idPauta);
    }
}
