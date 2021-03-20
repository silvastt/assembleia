package br.com.assembleia.converter;

import br.com.assembleia.bo.Pauta;
import br.com.assembleia.dto.PautaDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.util.Assert;
import org.springframework.stereotype.Component;

@Component("PautaConverter")
public class PautaConverter {

    private static final String ERRO_CONVERSAO_PAUTA = "Erro ao converter pauta!";
    private ModelMapper mapper;

    public PautaConverter(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public Pauta toModel(PautaDTO dto) {
        Assert.notNull(dto, ERRO_CONVERSAO_PAUTA);
        return mapper.map(dto, Pauta.class);
    }

    public PautaDTO toDTO(Pauta pauta) {
        Assert.notNull(pauta, ERRO_CONVERSAO_PAUTA);
        PautaDTO pautaDto = mapper.map(pauta, PautaDTO.class);
        return pautaDto;
    }

}
