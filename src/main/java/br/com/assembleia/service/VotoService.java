package br.com.assembleia.service;

import br.com.assembleia.bo.Associado;
import br.com.assembleia.bo.Controle;
import br.com.assembleia.bo.Pauta;
import br.com.assembleia.client.AssociadoClient;
import br.com.assembleia.converter.VotoConverter;
import br.com.assembleia.dto.VotoDTO;
import br.com.assembleia.error.ErroInternoException;
import br.com.assembleia.repository.VotoRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service("VotoService")
public class VotoService {

    private static final String ERRO_VOTAR = "Erro ao votar!";
    private static final String VOTO_REGISTRADO = "Voto registrado com sucesso!";
    private static final String PAUTA_NAO_ENCONTRADA = "Pauta não encontrada!";
    private static final String ASSOCIADO_JA_VOTOU = "Associado já votou nessa pauta!";
    private static final String SESSAO_FECHADA = "Sessão fechada!";
    private static final String ASSOCIADO_INVALIDO = "Associado inválido!";
    private static final String ERRO_BUSCAR_ASSOCIADO = "Erro ao buscar associado!";

    private final VotoRepository votoRepository;
    private final VotoConverter votoConverter;
    private final PautaService pautaService;
    private final ControleService controleService;
    private final AssociadoClient associadoClient;

    @Autowired
    public VotoService(VotoRepository votoRepository,
                       VotoConverter votoConverter,
                       PautaService pautaService,
                       ControleService controleService,
                       AssociadoClient associadoClient) {
        this.votoRepository = votoRepository;
        this.votoConverter = votoConverter;
        this.pautaService = pautaService;
        this.controleService = controleService;
        this.associadoClient = associadoClient;
    }

    public String votar(String idAssociado, VotoDTO votoDTO) {
        validaAssociado(idAssociado);
        validaPauta(votoDTO);
        validaControle(idAssociado, votoDTO.getIdPauta());

        try {
            votoRepository.save(votoConverter.toModel(votoDTO));
            registraControleVoto(idAssociado, votoDTO.getIdPauta());
        } catch (Exception e) {
            throw new ErroInternoException(ERRO_VOTAR);
        }

        return VOTO_REGISTRADO;
    }

    private void validaAssociado(String idAssociado) {
        try {
            Associado associado = associadoClient.buscarAssociado(idAssociado).getBody();
        } catch (FeignException fe) {
            throw new ErroInternoException((fe.status() == 404) ? ASSOCIADO_INVALIDO : ERRO_BUSCAR_ASSOCIADO);
        } catch (Exception e) {
            throw new ErroInternoException(ERRO_BUSCAR_ASSOCIADO);
        }
    }

    private void validaControle(String idAssociado, String idPauta) {
        Optional<Controle> controle = controleService.buscarControle(idAssociado, idPauta);
        if (controle.isPresent()) {
            throw new ErroInternoException(ASSOCIADO_JA_VOTOU);
        }
    }

    private void validaPauta(VotoDTO votoDTO) {
        Optional<Pauta> pauta = pautaService.buscarPauta(votoDTO.getIdPauta());
        if (!pauta.isPresent()) {
            throw new ErroInternoException(PAUTA_NAO_ENCONTRADA);
        }

        if (!isSessaoAberta(pauta.get())) {
            throw new ErroInternoException(SESSAO_FECHADA);
        }
    }

    private Boolean isSessaoAberta(Pauta pauta) {
        LocalDateTime agora = LocalDateTime.now();

        if (agora.isAfter(pauta.getSessao().getAbertura()) &&
            agora.isBefore(pauta.getSessao().getFechamento())) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    private void registraControleVoto(String idAssociado, String idPauta) {
        controleService.registrar(Controle.builder()
                                          .idPauta(idPauta)
                                          .idAssociado(idAssociado)
                                          .build());
    }
}
