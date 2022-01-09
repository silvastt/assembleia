package br.com.assembleia.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SessaoDto {
    private LocalDateTime abertura;
    private int duracao;
    private boolean aberta;
}
