package br.com.assembleia.validate;

import br.com.assembleia.dto.PautaDTO;
import br.com.assembleia.error.ErroInternoException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component("PautaValidate")
public class PautaValidate {

    private static final String TITULO_PAUTA_VAZIO = "Titulo da Pauta não informado!";
    private static final String DESCRICAO_PAUTA_VAZIO = "Descrição da Pauta não informada!";
    private static final String PAUTA_VAZIA = "Dados de pauta não informados!";

    public PautaValidate() {}

    public void validate(PautaDTO dto) throws Exception {
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
