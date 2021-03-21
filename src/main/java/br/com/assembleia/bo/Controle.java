package br.com.assembleia.bo;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "controle")
public class Controle {
    @Id
    private ObjectId id;
    private String idPauta;
    private String idAssociado;
}
