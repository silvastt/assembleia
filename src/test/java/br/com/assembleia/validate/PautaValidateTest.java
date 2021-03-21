package br.com.assembleia.validate;

import br.com.assembleia.dto.PautaDTO;
import br.com.assembleia.dto.SessaoDTO;
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
        PautaDTO pauta = buildPauta();
        pauta.setTitulo(null);

        try {
            pautaValidate.validate(pauta);
        } catch (ErroInternoException e) {
            Assert.assertEquals("Titulo da Pauta não informado!", e.getMessage());
        }
    }

    @Test
    public void deveValidarDescricaoVazio() throws Exception {
        PautaDTO pauta = buildPauta();
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

    private PautaDTO buildPauta() {
        return PautaDTO.builder().titulo("Titulo").descricao("Descricao").sessao(BuildSessao()).build();
    }

    private SessaoDTO BuildSessao() {
        return SessaoDTO.builder().abertura(LocalDateTime.now()).fechamento(LocalDateTime.now().plusMinutes(10)).build();
    }

}
