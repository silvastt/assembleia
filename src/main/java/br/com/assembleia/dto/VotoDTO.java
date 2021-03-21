package br.com.assembleia.dto;

import lombok.Data;

@Data
public class VotoDTO {

    private String idPauta;
    private VotoEnum voto;

}
