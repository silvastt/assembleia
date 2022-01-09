package br.com.assembleia.service;

import br.com.assembleia.bo.Associado;
import br.com.assembleia.bo.Controle;
import br.com.assembleia.bo.Pauta;
import br.com.assembleia.bo.Voto;
import br.com.assembleia.client.AssociadoClient;
import br.com.assembleia.client.ValidadorCpfClient;
import br.com.assembleia.converter.VotoConverter;
import br.com.assembleia.dto.SessaoDto;
import br.com.assembleia.dto.StatusDto;
import br.com.assembleia.dto.VotoDto;
import br.com.assembleia.dto.VotoEnum;
import br.com.assembleia.error.ErroInternoException;
import br.com.assembleia.repository.VotoRepository;
import br.com.assembleia.validate.VotoValidate;
import feign.FeignException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class VotoServiceTest {

    private static final String ID_PAUTA = "123456";
    private static final String DESCRICAO_PAUTA = "Descricao";
    private static final String TITULO_PAUTA = "Titulo";
    private static final String ID_ASSOCIADO = "123321";
    private static final String CPF = "82909598004";
    private static final String NOME = "Nome Associado";

    private VotoService votoService;
    private VotoRepository votoRepository;
    private VotoConverter votoConverter;
    private VotoValidate votoValidate;
    private PautaService pautaService;
    private ControleService controleService;
    private AssociadoClient associadoClient;
    private ValidadorCpfClient validadorCpfClient;

    @Before
    public void setup() {
        votoRepository = mock(VotoRepository.class);
        votoConverter = mock(VotoConverter.class);
        votoValidate = mock(VotoValidate.class);
        pautaService = mock(PautaService.class);
        controleService = mock(ControleService.class);
        associadoClient = mock(AssociadoClient.class);
        validadorCpfClient = mock(ValidadorCpfClient.class);
        votoService = new VotoService(votoRepository, votoConverter, votoValidate, pautaService, controleService, associadoClient, validadorCpfClient);

        when(associadoClient.buscarAssociado(ID_ASSOCIADO)).thenReturn(buildAssociado());
        when(pautaService.buscarPauta(ID_PAUTA)).thenReturn(Optional.of(buildPauta()));
        when(validadorCpfClient.validaCpf(CPF)).thenReturn(getStatusValido());
    }

    @Test
    public void deveBuscarVotos() {
        votoService.buscarVotosPorPauta(ID_PAUTA);
    }

    @Test
    public void deveVotar() {
        votoService.votar(ID_ASSOCIADO, buildVoto());
    }

    @Test
    public void naoVotaErroSessaoFechada() {
        Pauta pauta = buildPauta();
        pauta.getSessao().setAberta(false);
        when(pautaService.buscarPauta(ID_PAUTA)).thenReturn(Optional.of(pauta));

        try {
            votoService.votar(ID_ASSOCIADO, buildVoto());
        } catch (ErroInternoException e) {
            assertEquals("Sessão fechada!", e.getMessage());
        }
    }

    @Test
    public void naoVotaCPFInvalido() {
        when(validadorCpfClient.validaCpf(CPF)).thenReturn(getStatusInvalido());
        try {
            votoService.votar(ID_ASSOCIADO, buildVoto());
        } catch (ErroInternoException e) {
            assertEquals("Erro ao buscar associado!", e.getMessage());
        }
    }

    @Test
    public void naoVotaErroAoVotar() {
        doThrow(ErroInternoException.class).when(votoRepository).save(any(Voto.class));

        try {
            votoService.votar(ID_ASSOCIADO, buildVoto());
        } catch (ErroInternoException e) {
            assertEquals("Erro ao buscar associado!", e.getMessage());
        }
    }

    @Test
    public void naoVotaSessaoFechada() {
        Pauta pauta = buildPauta();
        when(pautaService.buscarPauta(ID_PAUTA)).thenReturn(Optional.of(pauta));

        try {
            votoService.votar(ID_ASSOCIADO, buildVoto());
        } catch (ErroInternoException e) {
            assertEquals("Sessão fechada!", e.getMessage());
        }
    }

    @Test
    public void naoVotaPautaNaoEncontrada() {
        when(pautaService.buscarPauta(ID_PAUTA)).thenReturn(Optional.empty());

        try {
            votoService.votar(ID_ASSOCIADO, buildVoto());
        } catch (ErroInternoException e) {
            assertEquals("Pauta não encontrada!", e.getMessage());
        }
    }

    @Test
    public void naoVotaAssociadoJaVotou() {
        when(controleService.buscarControle(ID_ASSOCIADO, ID_PAUTA)).thenReturn(Optional.of(buildControle()));

        try {
            votoService.votar(ID_ASSOCIADO, buildVoto());
        } catch (ErroInternoException e) {
            assertEquals("Associado já votou nessa pauta!", e.getMessage());
        }
    }

    @Test
    public void naoVotaErroBuscarAssociado() {
        doThrow(ErroInternoException.class).when(associadoClient).buscarAssociado(any(String.class));

        try {
            votoService.votar(ID_ASSOCIADO, buildVoto());
        } catch (ErroInternoException e) {
            assertEquals("Erro ao buscar associado!", e.getMessage());
        }
    }

    @Test
    public void naoVotaErroAssociadoInvalido() {
        doThrow(FeignException.class).when(associadoClient).buscarAssociado(any(String.class));

        try {
            votoService.votar(ID_ASSOCIADO, buildVoto());
        } catch (ErroInternoException e) {
            assertEquals("Erro ao buscar associado!", e.getMessage());
        }
    }

    private VotoDto buildVoto() {
        return VotoDto.builder().voto(VotoEnum.SIM).idPauta(ID_PAUTA).build();
    }

    private ResponseEntity<Associado> buildAssociado() {
        ResponseEntity<Associado> entity = ResponseEntity.of(Optional.of(buildAssociadoDto()));
        return entity;
    }

    private Associado buildAssociadoDto() {
        Associado associado = new Associado();
        associado.setCpf(CPF);
        associado.setNome(NOME);
        return associado;
    }

    private Pauta buildPauta() {
        Pauta pauta = new Pauta();
        pauta.setDescricao(DESCRICAO_PAUTA);
        pauta.setTitulo(TITULO_PAUTA);
        pauta.setSessao(getSessaoAberta());

        return pauta;
    }

    private SessaoDto getSessaoAberta() {
        return SessaoDto.builder().abertura(LocalDateTime.now().minusMonths(1)).duracao(1).aberta(true).build();
    }

    private Controle buildControle() {
        return Controle.builder().idPauta(ID_PAUTA).idAssociado(ID_ASSOCIADO).build();
    }

    private ResponseEntity<StatusDto> getStatusValido() {
        return ResponseEntity.ok().body(StatusDto.builder().status("ABLE_TO_VOTE").build());
    }

    private ResponseEntity<StatusDto> getStatusInvalido() {
        return ResponseEntity.ok().body(StatusDto.builder().status("UNABLE_TO_VOTE").build());
    }

}
