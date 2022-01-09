package br.com.assembleia.service;

import br.com.assembleia.bo.Pauta;
import br.com.assembleia.converter.PautaConverter;
import br.com.assembleia.dto.PautaDto;
import br.com.assembleia.dto.SessaoDto;
import br.com.assembleia.error.ErroInternoException;
import br.com.assembleia.repository.PautaRepository;
import br.com.assembleia.validate.PautaValidate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static br.com.assembleia.util.MessagesProperties.*;

@Service("PautaService")
public class PautaService {

    Logger logger = LoggerFactory.getLogger(PautaService.class);

    private final PautaRepository repository;
    private final PautaConverter converter;
    private final PautaValidate validate;

    public PautaService(
        final PautaRepository repository,
        final PautaConverter converter,
        final PautaValidate validate) {
        this.repository = repository;
        this.converter = converter;
        this.validate = validate;
    }

    public Pauta criarPauta(final PautaDto pautaDto) {
        validate.validate(pautaDto);
        pautaDto.setSessao(SessaoDto.builder().build());
        try {
            logger.info("Salvando Pauta: {}", pautaDto.getTitulo());
            return repository.save(converter.toModel(pautaDto));
        } catch (Exception e) {
            logger.error("Erro ao salvar pauta: {}", pautaDto.getTitulo());
            throw new ErroInternoException(ERRO_CRIAR_PAUTA);
        }
    }

    public String abrirSessao(final String idPauta, final SessaoDto sessao) {
        final Optional<Pauta> optionalPauta = buscarPauta(idPauta);
        Pauta pauta = validaPauta(optionalPauta);

        pauta.setSessao(validaSessao(sessao));
        pauta.getSessao().setAbertura(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        pauta.getSessao().setAberta(true);
        logger.info("Salvando abertura da sessao: [Pauta] {}", idPauta);
        repository.save(pauta);

        return SESSAO_ABERTA_SUCESSO;
    }

    public Optional<Pauta> buscarPauta(final String idPauta) {
        logger.info("Buscando Pauta: {}", idPauta);
        return repository.findById(idPauta);
    }

    public List<Pauta> buscarSessoesAbertas() {
        return repository.findByPautasAbertas();
    }

    public void fecharSessao(final Pauta pauta) {
        pauta.getSessao().setAberta(false);
        repository.save(pauta);
    }

    private SessaoDto validaSessao(final SessaoDto sessao) {
        logger.info("Validando sessão:");
        if (Objects.isNull(sessao.getDuracao())) {
            sessao.setDuracao(1);
        }

        return sessao;
    }

    private Pauta validaPauta(final Optional<Pauta> pauta) {
        if (!pauta.isPresent()) {
            logger.error("Pauta não encontrada");
            throw new ErroInternoException(PAUTA_NAO_ENCONTRADA);
        }

        if (!Objects.isNull(pauta.get().getSessao().getAbertura())) {
            logger.error("Pauta já aberta: [Pauta] {}", pauta.get().getId());
            throw new ErroInternoException(PAUTA_JA_ABERTA);
        }

        return pauta.get();
    }
}
