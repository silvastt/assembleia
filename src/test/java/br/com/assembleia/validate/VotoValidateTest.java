package br.com.assembleia.validate;

import br.com.assembleia.dto.VotoDto;
import br.com.assembleia.dto.VotoEnum;
import br.com.assembleia.error.ErroInternoException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class VotoValidateTest {

    private VotoValidate votoValidate;

    @Before
    public void setup() {
        votoValidate = new VotoValidate();
    }

    @Test
    public void deveValidarComSucesso() throws Exception {
        votoValidate.validate(buildVoto());
    }

    @Test
    public void deveValidarPautaVazio() throws Exception {
        VotoDto voto = buildVoto();
        voto.setIdPauta(null);

        try {
            votoValidate.validate(voto);
        } catch (ErroInternoException e) {
            Assert.assertEquals("ID da Pauta não informado!", e.getMessage());
        }
    }

    @Test
    public void deveValidarObjetoVotoVazio() throws Exception {
        try {
            votoValidate.validate(null);
        } catch (ErroInternoException e) {
            Assert.assertEquals("Dados de voto não informados!", e.getMessage());
        }
    }

    private VotoDto buildVoto() {
        return VotoDto.builder().voto(VotoEnum.SIM).idPauta("123456").build();
    }

}
