package br.com.assembleia.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VotoDTO {

    private String idPauta;
    private VotoEnum voto;
    private LocalDateTime abertura;
    private LocalDateTime fechamento;

}
