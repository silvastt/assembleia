package br.com.assembleia.validate;

import br.com.assembleia.dto.VotoDTO;
import br.com.assembleia.error.ErroInternoException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component("VotoValidate")
public class VotoValidate {

    private static final String ID_PAUTA_VAZIO = "ID da Pauta não informado!";
    private static final String VOTO_VAZIO = "Dados de voto não informados!";

    public VotoValidate() {}

    public void validate(VotoDTO dto) throws Exception {
        if (Objects.isNull(dto)) {
            throw new ErroInternoException(VOTO_VAZIO);
        }

        if (StringUtils.isEmpty(dto.getIdPauta())) {
            throw new ErroInternoException(ID_PAUTA_VAZIO);
        }
    }
}
