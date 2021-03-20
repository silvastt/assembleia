package br.com.assembleia.converter;

import br.com.assembleia.bo.Voto;
import br.com.assembleia.dto.VotoDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.util.Assert;
import org.springframework.stereotype.Component;

@Component("VotoConverter")
public class VotoConverter {

    private static final String ERRO_CONVERSAO_VOTO = "Erro ao converter voto!";
    private ModelMapper mapper;

    public VotoConverter(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public Voto toModel(VotoDTO dto) {
        Assert.notNull(dto, ERRO_CONVERSAO_VOTO);
        return mapper.map(dto, Voto.class);
    }

    public VotoDTO toDTO(Voto voto) {
        Assert.notNull(voto, ERRO_CONVERSAO_VOTO);
        VotoDTO votoDTO = mapper.map(voto, VotoDTO.class);
        return votoDTO;
    }

}
