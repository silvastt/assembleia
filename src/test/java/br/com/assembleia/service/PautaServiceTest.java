package br.com.assembleia.service;

import br.com.assembleia.bo.Pauta;
import br.com.assembleia.converter.PautaConverter;
import br.com.assembleia.dto.PautaDTO;
import br.com.assembleia.dto.SessaoDTO;
import br.com.assembleia.error.ErroInternoException;
import br.com.assembleia.producer.AberturaSessaoProducer;
import br.com.assembleia.repository.PautaRepository;
import br.com.assembleia.validate.PautaValidate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class PautaServiceTest {

    private static final String ID_PAUTA = "123465";
    private static final String DESCRICAO_PAUTA = "Descricao";
    private static final String TITULO_PAUTA = "Titulo";

    private PautaRepository pautaRepository;
    private PautaConverter pautaConverter;
    private PautaValidate pautaValidate;
    private PautaService pautaService;
    private AberturaSessaoProducer aberturaSessaoProducer;

    @Before
    public void setup() {
        pautaRepository = mock(PautaRepository.class);
        pautaConverter = mock(PautaConverter.class);
        pautaValidate = mock(PautaValidate.class);
        aberturaSessaoProducer = mock(AberturaSessaoProducer.class);
        pautaService = new PautaService(pautaRepository, pautaConverter, pautaValidate, aberturaSessaoProducer);

        when(pautaRepository.save(any(Pauta.class))).thenReturn(buildPautaBo());
        when(pautaConverter.toModel(any(PautaDTO.class))).thenReturn(buildPautaBo());
        when(pautaRepository.findById(ID_PAUTA)).thenReturn(Optional.of(buildPautaBo()));
    }

    @Test
    public void deveCriarPauta() throws Exception {
        Pauta pauta = pautaService.criarPauta(buildPauta());

        assertEquals(TITULO_PAUTA, pauta.getTitulo());
        assertEquals(DESCRICAO_PAUTA, pauta.getDescricao());
    }

    @Test
    public void deveAbrirSessao() throws Exception {
        when(pautaRepository.findById(ID_PAUTA)).thenReturn(Optional.of(buildPautaBoSemSessao()));
        String resposta = pautaService.abrirSessao(ID_PAUTA, buildSessao());

        assertEquals("Sess??o aberta com sucesso!", resposta);
    }

    @Test
    public void deveAbrirSessaoAutomatica() throws Exception {
        when(pautaRepository.findById(ID_PAUTA)).thenReturn(Optional.of(buildPautaBoSemSessao()));
        String resposta = pautaService.abrirSessao(ID_PAUTA, SessaoDTO.builder().build());

        assertEquals("Sess??o aberta com sucesso!", resposta);
    }

    @Test
    public void deveNaoCriarPauta() throws Exception {
        doThrow(ErroInternoException.class).when(pautaRepository).save(any(Pauta.class));
        try {
            Pauta pauta = pautaService.criarPauta(buildPauta());
        } catch (ErroInternoException e) {
            assertEquals("Erro ao criar pauta!", e.getMessage());
        }
    }

    @Test
    public void testPautaNaoEncontrada() throws Exception {
        when(pautaRepository.findById(ID_PAUTA)).thenReturn(Optional.empty());
        try {
            String Resultado = pautaService.abrirSessao(ID_PAUTA, buildSessao());
        } catch (ErroInternoException e) {
            assertEquals("Pauta n??o encontrada!", e.getMessage());
        }
    }

    @Test
    public void testPautaJaAberta() throws Exception {
        try {
            String Resultado = pautaService.abrirSessao(ID_PAUTA, buildSessao());
        } catch (ErroInternoException e) {
            assertEquals("Pauta j?? aberta!", e.getMessage());
        }
    }

    private Pauta buildPautaBoSemSessao() {
        Pauta pauta = new Pauta();
        pauta.setDescricao(DESCRICAO_PAUTA);
        pauta.setTitulo(TITULO_PAUTA);
        pauta.setSessao(SessaoDTO.builder().build());

        return pauta;
    }

    private Pauta buildPautaBo() {
        Pauta pauta = new Pauta();
        pauta.setDescricao(DESCRICAO_PAUTA);
        pauta.setTitulo(TITULO_PAUTA);
        pauta.setSessao(buildSessao());

        return pauta;
    }

    private PautaDTO buildPauta() {
        return PautaDTO.builder()
                       .titulo(TITULO_PAUTA)
                       .descricao(DESCRICAO_PAUTA)
                       .sessao(buildSessao())
                       .build();
    }

    private SessaoDTO buildSessao() {
        return SessaoDTO.builder().abertura(LocalDateTime.now()).fechamento(LocalDateTime.now()).build();
    }

}
