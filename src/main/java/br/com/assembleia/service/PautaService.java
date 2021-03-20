package br.com.assembleia.service;

import br.com.assembleia.bo.Pauta;
import br.com.assembleia.converter.PautaConverter;
import br.com.assembleia.dto.PautaDTO;
import br.com.assembleia.dto.SessaoDTO;
import br.com.assembleia.error.ErroInternoException;
import br.com.assembleia.repository.PautaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service("PautaService")
public class PautaService {

    private static final String ERRO_CRIAR_PAUTA = "Erro ao criar associado!";
    private static final String SESSAO_ABERTA_SUCESSO = "Sessão aberta com sucesso!";
    private static final String PAUTA_NAO_ENCONTRADA = "Pauta não encontrada!";
    private static final String PAUTA_JA_ABERTA = "Pauta já aberta!";

    private final PautaRepository pautaRepository;
    private final PautaConverter pautaConverter;

    @Autowired
    public PautaService(PautaRepository pautaRepository,
                        PautaConverter pautaConverter) {
        this.pautaRepository = pautaRepository;
        this.pautaConverter = pautaConverter;
    }

    public Pauta criarPauta(PautaDTO dto) throws Exception {
        try {
            return pautaRepository.save(pautaConverter.toModel(dto));
        } catch (Exception e) {
            throw new ErroInternoException(ERRO_CRIAR_PAUTA);
        }
    }


    public String abrirSessao(String idPauta, SessaoDTO sessao) {
        Optional<Pauta> pauta = pautaRepository.findById(idPauta);
        validaPauta(pauta);

        pauta.get().setSessao(sessao);
        pautaRepository.save(pauta.get());
        return SESSAO_ABERTA_SUCESSO;
    }

    private void validaPauta(Optional<Pauta> pauta) {
        if (!pauta.isPresent()) {
            throw new ErroInternoException(PAUTA_NAO_ENCONTRADA);
        }

        if (!Objects.isNull(pauta.get().getSessao().getAbertura())) {
            throw new ErroInternoException(PAUTA_JA_ABERTA);
        }
    }
}
