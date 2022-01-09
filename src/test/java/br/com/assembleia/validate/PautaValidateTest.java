package br.com.assembleia.validate;

import br.com.assembleia.dto.PautaDto;
import br.com.assembleia.dto.SessaoDto;
import br.com.assembleia.error.ErroInternoException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
public class PautaValidateTest {

    private PautaValidate pautaValidate;

    @Before
    public void setup() {
        pautaValidate = new PautaValidate();
    }

    @Test
    public void deveValidarComSucesso() throws Exception {
        pautaValidate.validate(buildPauta());
    }

    @Test
    public void deveValidarTituloVazio() throws Exception {
        PautaDto pauta = buildPauta();
        pauta.setTitulo(null);

        try {
            pautaValidate.validate(pauta);
        } catch (ErroInternoException e) {
            Assert.assertEquals("Titulo da Pauta não informado!", e.getMessage());
        }
    }

    @Test
    public void deveValidarDescricaoVazio() throws Exception {
        PautaDto pauta = buildPauta();
        pauta.setDescricao(null);

        try {
            pautaValidate.validate(pauta);
        } catch (ErroInternoException e) {
            Assert.assertEquals("Descrição da Pauta não informada!", e.getMessage());
        }
    }

    @Test
    public void deveValidarPautaVazio() throws Exception {
        try {
            pautaValidate.validate(null);
        } catch (ErroInternoException e) {
            Assert.assertEquals("Dados de pauta não informados!", e.getMessage());
        }
    }

    private PautaDto buildPauta() {
        return PautaDto.builder().titulo("Titulo").descricao("Descricao").build();
    }

    private SessaoDto BuildSessao() {
        return SessaoDto.builder().abertura(LocalDateTime.now()).duracao(1).aberta(false).build();
    }

}
