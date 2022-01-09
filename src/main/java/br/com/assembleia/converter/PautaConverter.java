package br.com.assembleia.converter;

import br.com.assembleia.bo.Pauta;
import br.com.assembleia.dto.PautaDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.util.Assert;
import org.springframework.stereotype.Component;

import static br.com.assembleia.util.MessagesProperties.ERRO_CONVERSAO_PAUTA;

@Component("PautaConverter")
public class PautaConverter {

    private ModelMapper mapper;

    public PautaConverter(final ModelMapper mapper) {
        this.mapper = mapper;
    }

    public Pauta toModel(final PautaDto dto) {
        Assert.notNull(dto, ERRO_CONVERSAO_PAUTA);
        return mapper.map(dto, Pauta.class);
    }

    public PautaDto toDTO(final Pauta pauta) {
        Assert.notNull(pauta, ERRO_CONVERSAO_PAUTA);
        return PautaDto.builder()
                       .id(pauta.getId().toString())
                       .titulo(pauta.getTitulo())
                       .descricao(pauta.getDescricao())
                       .build();
    }

}
