package br.com.assembleia.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AberturaSessaoProducer {

    @Value("${topico.nome}")
    private String topico;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void send(String idPauta){
        kafkaTemplate.send(topico, idPauta);
    }

}