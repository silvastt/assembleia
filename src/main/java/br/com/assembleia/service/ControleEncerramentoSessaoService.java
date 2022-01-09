package br.com.assembleia.service;

import br.com.assembleia.bo.Pauta;
import br.com.assembleia.producer.EncerramentoSessaoProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service("ControleEncerramentoSessao")
public class ControleEncerramentoSessaoService {

    Logger logger = LoggerFactory.getLogger(ControleEncerramentoSessaoService.class);

    private final PautaService pautaService;
    private final EncerramentoSessaoProducer encerramentoSessaoProducer;
    private final ResultadoService resultadoService;

    public ControleEncerramentoSessaoService(
        final PautaService pautaService,
        final EncerramentoSessaoProducer encerramentoSessaoProducer,
        final ResultadoService resultadoService){
        this.pautaService = pautaService;
        this.encerramentoSessaoProducer = encerramentoSessaoProducer;
        this.resultadoService = resultadoService;
    }

    @Scheduled(fixedDelay = 30000)
    public void verificaFechamentoSessao() {
        List<Pauta> pautas = pautaService.buscarSessoesAbertas();
        logger.info("Sessoes abertas: {}", pautas);
        for (Pauta pauta : pautas) {
            LocalDateTime horaFechamento = pauta.getSessao().getAbertura().plusMinutes(pauta.getSessao().getDuracao());
            LocalDateTime agora = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
            if (horaFechamento.isBefore(agora)) {
                pautaService.fecharSessao(pauta);
                logger.info("Sessao encerrada: {}", pauta.getId());
                encerramentoSessaoProducer.send(resultadoService.gerarResultado(pauta.getId().toString()));
            }
        }
    }

}
