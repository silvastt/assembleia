package br.com.assembleia.validate;

import br.com.assembleia.dto.VotoDto;
import br.com.assembleia.error.ErroInternoException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static br.com.assembleia.util.MessagesProperties.ID_PAUTA_VAZIO;
import static br.com.assembleia.util.MessagesProperties.VOTO_VAZIO;

@Component("VotoValidate")
public class VotoValidate {

    public VotoValidate() {}

    public void validate(VotoDto dto) {
        if (Objects.isNull(dto)) {
            throw new ErroInternoException(VOTO_VAZIO);
        }

        if (StringUtils.isEmpty(dto.getIdPauta())) {
            throw new ErroInternoException(ID_PAUTA_VAZIO);
        }
    }
}
