package br.com.assembleia.producer;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AberturaSessaoConsumer {

    Logger logger = LoggerFactory.getLogger(AberturaSessaoConsumer.class);

    @Value("${topico.nome")
    private String topico;

    @KafkaListener(topics = "${topico.nome}", groupId = "sessao")
    public void consume(ConsumerRecord<String, String> dados){
        logger.info("[-------------Abertura da sessão[-------------]: " + dados.value());
    }

}