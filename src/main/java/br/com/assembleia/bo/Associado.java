package br.com.assembleia.bo;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "associado")
public class Associado {
    @Id
    private ObjectId id;
    private String nome;
    private String cpf;
}
