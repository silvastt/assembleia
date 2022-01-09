package br.com.assembleia.validate;

import br.com.assembleia.dto.PautaDto;
import br.com.assembleia.error.ErroInternoException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static br.com.assembleia.util.MessagesProperties.*;

@Component("PautaValidate")
public class PautaValidate {

    public PautaValidate() {}

    public void validate(PautaDto dto) {
        if (Objects.isNull(dto)) {
            throw new ErroInternoException(PAUTA_VAZIA);
        }

        if (StringUtils.isEmpty(dto.getTitulo())) {
            throw new ErroInternoException(TITULO_PAUTA_VAZIO);
        }

        if (StringUtils.isEmpty(dto.getDescricao())) {
            throw new ErroInternoException(DESCRICAO_PAUTA_VAZIO);
        }
    }
}
