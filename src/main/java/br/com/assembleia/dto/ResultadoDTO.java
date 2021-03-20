package br.com.assembleia.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResultadoDTO {

    private PautaDTO pauta;
    private List<ItemVotoDTO> votos;

}
