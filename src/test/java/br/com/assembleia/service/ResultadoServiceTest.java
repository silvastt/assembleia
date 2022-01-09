package br.com.assembleia.service;

import br.com.assembleia.bo.Pauta;
import br.com.assembleia.bo.Voto;
import br.com.assembleia.converter.PautaConverter;
import br.com.assembleia.dto.ResultadoDto;
import br.com.assembleia.dto.SessaoDto;
import br.com.assembleia.dto.VotoEnum;
import br.com.assembleia.error.ErroInternoException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ResultadoServiceTest {

    private static final String ID_PAUTA = "123465";
    private static final String DESCRICAO_PAUTA = "Descricao";
    private static final String TITULO_PAUTA = "Titulo";

    private PautaService pautaService;
    private PautaConverter pautaConverter;
    private VotoService votoService;
    private ResultadoService resultadoService;

    @Before
    public void setup() {
        pautaService = mock(PautaService.class);
        pautaConverter = mock(PautaConverter.class);
        votoService = mock(VotoService.class);
        resultadoService = new ResultadoService(pautaService, pautaConverter, votoService);

        when(pautaService.buscarPauta(ID_PAUTA)).thenReturn(buildPauta());
        when(votoService.buscarVotosPorPauta(ID_PAUTA)).thenReturn(buildVotos());
    }

    @Test
    public void deveGerarResultado() throws Exception {
        ResultadoDto resultadoDTO = resultadoService.gerarResultado(ID_PAUTA);

        assertEquals(2, resultadoDTO.getVotos().size());
        assertEquals(1, resultadoDTO.getVotos().get(0).getQuantidadeVotos().intValue());
        assertEquals(0, resultadoDTO.getVotos().get(1).getQuantidadeVotos().intValue());
    }

    @Test
    public void deveGerarResultadoErro() throws Exception {
        when(pautaService.buscarPauta(ID_PAUTA)).thenReturn(null);
        try {
            ResultadoDto resultadoDTO = resultadoService.gerarResultado(ID_PAUTA);
        } catch (ErroInternoException e) {
            assertEquals("Erro ao gerar relatório!", e.getMessage());
        }
    }

    private Optional<Pauta> buildPauta() {
        Pauta pauta = new Pauta();
        pauta.setDescricao(DESCRICAO_PAUTA);
        pauta.setTitulo(TITULO_PAUTA);
        pauta.setSessao(buildSessao());

        return Optional.of(pauta);
    }

    private SessaoDto buildSessao() {
        return SessaoDto.builder().abertura(LocalDateTime.now()).duracao(1).aberta(false).build();
    }

    private List<Voto> buildVotos() {
        List<Voto> votos = new ArrayList<>();
        Voto voto = new Voto();
        voto.setVoto(VotoEnum.SIM);
        voto.setIdPauta(ID_PAUTA);
        votos.add(voto);
        return votos;
    }

}
