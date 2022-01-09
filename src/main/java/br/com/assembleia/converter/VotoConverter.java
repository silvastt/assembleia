package br.com.assembleia.converter;

import br.com.assembleia.bo.Voto;
import br.com.assembleia.dto.VotoDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.util.Assert;
import org.springframework.stereotype.Component;

import static br.com.assembleia.util.MessagesProperties.ERRO_CONVERSAO_VOTO;

@Component("VotoConverter")
public class VotoConverter {

    private ModelMapper mapper;

    public VotoConverter(final ModelMapper mapper) {
        this.mapper = mapper;
    }

    public Voto toModel(final VotoDto dto) {
        Assert.notNull(dto, ERRO_CONVERSAO_VOTO);
        return mapper.map(dto, Voto.class);
    }

}
