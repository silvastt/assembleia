package br.com.assembleia.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResultadoDTO {

    private PautaDTO pauta;
    private List<ItemVotoDTO> votos;

}
