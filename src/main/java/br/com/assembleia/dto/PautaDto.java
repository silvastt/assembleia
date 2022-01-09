package br.com.assembleia.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PautaDto {
    private String id;
    private String titulo;
    private String descricao;
    private SessaoDto sessao;
}
