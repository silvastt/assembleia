package br.com.assembleia.dto;

import lombok.Data;

@Data
public class ItemVotoDTO {

    private VotoEnum item;
    private Integer quantidadeVotos;

}
