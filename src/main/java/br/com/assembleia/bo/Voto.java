package br.com.assembleia.bo;

import br.com.assembleia.dto.VotoEnum;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "voto")
public class Voto {
    @Id
    private ObjectId id;
    private String idPauta;
    private VotoEnum voto;
    private LocalDateTime data;
}
