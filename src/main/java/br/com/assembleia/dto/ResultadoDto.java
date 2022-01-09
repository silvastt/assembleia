package br.com.assembleia.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResultadoDto {
    private PautaDto pauta;
    private List<ItemVotoDto> votos;
}
