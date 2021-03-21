package br.com.assembleia.service;

import br.com.assembleia.bo.Controle;
import br.com.assembleia.error.ErroInternoException;
import br.com.assembleia.repository.ControleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("ControleService")
public class ControleService {

    private static final String ERRO_REGISTRAR_CONTROLE = "Erro ao registrar controle!s";

    private final ControleRepository controleRepository;

    @Autowired
    public ControleService(ControleRepository controleRepository) {
        this.controleRepository = controleRepository;
    }

    public void registrar(Controle controle) {
        try {
            controleRepository.save(controle);
        } catch (Exception e) {
            throw new ErroInternoException(ERRO_REGISTRAR_CONTROLE);
        }
    }

    public Optional<Controle> buscarControle(String idAssociado, String idPauta) {
        return controleRepository.findByIdAssociadoAndAndIdPauta(idAssociado, idPauta);
    }
}
