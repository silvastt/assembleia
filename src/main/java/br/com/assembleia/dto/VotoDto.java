package br.com.assembleia.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VotoDto {
    private String idPauta;
    private VotoEnum voto;
}
