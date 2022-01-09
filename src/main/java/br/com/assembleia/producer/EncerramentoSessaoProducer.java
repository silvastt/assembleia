package br.com.assembleia.producer;

import br.com.assembleia.dto.ResultadoDto;
import br.com.assembleia.service.ControleEncerramentoSessaoService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EncerramentoSessaoProducer {

    Logger logger = LoggerFactory.getLogger(ControleEncerramentoSessaoService.class);

    @Value("${topico.nome}")
    private String topico;

    private final KafkaTemplate<String, ResultadoDto> kafkaTemplate;

    public void send(final ResultadoDto resultadoService){
        try {
            kafkaTemplate.send(topico, resultadoService);
            logger.info("Resultado da sessão notificada {}" , resultadoService);
        } catch (Exception e) {
            logger.error("Erro ao notificar resultado da Sessao {}" , resultadoService);
        }
    }

}