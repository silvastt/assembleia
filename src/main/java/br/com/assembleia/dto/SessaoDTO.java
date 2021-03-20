package br.com.assembleia.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SessaoDTO {

    private LocalDateTime abertura;
    private LocalDateTime fechamento;

}
