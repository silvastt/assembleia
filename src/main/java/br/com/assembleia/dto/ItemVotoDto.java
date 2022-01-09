package br.com.assembleia.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemVotoDto {
    private VotoEnum item;
    private Integer quantidadeVotos;
}
