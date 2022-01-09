package br.com.assembleia.bo;

import br.com.assembleia.dto.SessaoDto;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "pauta")
public class Pauta {
    @Id
    private ObjectId id;
    private String titulo;
    private String descricao;
    private SessaoDto sessao;
}
