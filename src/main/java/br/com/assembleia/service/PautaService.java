package br.com.assembleia.service;

import br.com.assembleia.bo.Pauta;
import br.com.assembleia.converter.PautaConverter;
import br.com.assembleia.dto.PautaDTO;
import br.com.assembleia.dto.SessaoDTO;
import br.com.assembleia.error.ErroInternoException;
import br.com.assembleia.repository.PautaRepository;
import br.com.assembleia.validate.PautaValidate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service("PautaService")
public class PautaService {

    Logger logger = LoggerFactory.getLogger(PautaService.class);

    private static final String ERRO_CRIAR_PAUTA = "Erro ao criar pauta!";
    private static final String SESSAO_ABERTA_SUCESSO = "Sessão aberta com sucesso!";
    private static final String PAUTA_NAO_ENCONTRADA = "Pauta não encontrada!";
    private static final String PAUTA_JA_ABERTA = "Pauta já aberta!";

    private final PautaRepository pautaRepository;
    private final PautaConverter pautaConverter;
    private final PautaValidate pautaValidate;

    @Autowired
    public PautaService(PautaRepository pautaRepository,
                        PautaConverter pautaConverter,
                        PautaValidate pautaValidate) {
        this.pautaRepository = pautaRepository;
        this.pautaConverter = pautaConverter;
        this.pautaValidate = pautaValidate;
    }

    public Pauta criarPauta(PautaDTO dto) throws Exception {
        pautaValidate.validate(dto);
        try {
            logger.info("Salvando Pauta: " + dto.getTitulo());
            return pautaRepository.save(pautaConverter.toModel(dto));
        } catch (Exception e) {
            logger.error("Erro ao salvar pauta: " + dto.getTitulo());
            throw new ErroInternoException(ERRO_CRIAR_PAUTA);
        }
    }


    public String abrirSessao(String idPauta, SessaoDTO sessao) {
        Optional<Pauta> pauta = buscarPauta(idPauta);
        validaPauta(pauta);

        pauta.get().setSessao(validaSessao(sessao));
        logger.info("Salvando abertura da sessao: [Pauta] " + idPauta);
        pautaRepository.save(pauta.get());

        return SESSAO_ABERTA_SUCESSO;
    }

    public Optional<Pauta> buscarPauta(String idPauta) {
        logger.info("Buscando Pauta: " + idPauta);
        return pautaRepository.findById(idPauta);
    }

    private SessaoDTO validaSessao(SessaoDTO sessao) {
        logger.info("Validando sessão:");
        if (Objects.isNull(sessao.getAbertura())) {
            return gerarSessaoAutomatica();
        }

        return sessao;
    }

    private SessaoDTO gerarSessaoAutomatica() {
        logger.info("Gerando sessão automática:");
        LocalDateTime agora = LocalDateTime.now();
        return SessaoDTO.builder()
                        .abertura(agora)
                        .fechamento(agora.plusMinutes(1))
                        .build();
    }

    private void validaPauta(Optional<Pauta> pauta) {
        if (!pauta.isPresent()) {
            logger.error("Pauta não encontrada");
            throw new ErroInternoException(PAUTA_NAO_ENCONTRADA);
        }

        if (!Objects.isNull(pauta.get().getSessao().getAbertura())) {
            logger.error("Pauta já aberta: [Pauta] " + pauta.get().getId());
            throw new ErroInternoException(PAUTA_JA_ABERTA);
        }
    }

}
