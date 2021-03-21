package br.com.assembleia.service;

import br.com.assembleia.bo.Controle;
import br.com.assembleia.error.ErroInternoException;
import br.com.assembleia.repository.ControleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("ControleService")
public class ControleService {

    Logger logger = LoggerFactory.getLogger(ControleService.class);

    private static final String ERRO_REGISTRAR_CONTROLE = "Erro ao registrar controle!s";

    private final ControleRepository controleRepository;

    @Autowired
    public ControleService(ControleRepository controleRepository) {
        this.controleRepository = controleRepository;
    }

    public void registrar(Controle controle) {
        try {
            logger.info("Salvando controle de voto: [Pauta] " + controle.getIdPauta() + " [Associado] " + controle.getIdAssociado());
            controleRepository.save(controle);
        } catch (Exception e) {
            logger.error("Erro ao registrar controle: [Pauta] " + controle.getIdPauta() + " [Associado] " + controle.getIdAssociado());
            throw new ErroInternoException(ERRO_REGISTRAR_CONTROLE);
        }
    }

    public Optional<Controle> buscarControle(String idAssociado, String idPauta) {
        logger.info("Buscando dados de controle: [Pauta] " + idPauta + " [Associado] " + idAssociado);
        return controleRepository.findByIdAssociadoAndAndIdPauta(idAssociado, idPauta);
    }
}
