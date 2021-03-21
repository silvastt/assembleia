package br.com.assembleia.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SessaoDTO {

    private LocalDateTime abertura;
    private LocalDateTime fechamento;

}
