package br.com.assembleia.dto;

import lombok.Data;

@Data
public class PautaDTO {

    private String titulo;
    private String descricao;
    private SessaoDTO sessao;

}
