package br.com.assembleia.service;

import br.com.assembleia.bo.Associado;
import br.com.assembleia.bo.Controle;
import br.com.assembleia.bo.Pauta;
import br.com.assembleia.bo.Voto;
import br.com.assembleia.client.AssociadoClient;
import br.com.assembleia.client.ValidadorCpfClient;
import br.com.assembleia.converter.VotoConverter;
import br.com.assembleia.dto.StatusDto;
import br.com.assembleia.dto.VotoDto;
import br.com.assembleia.error.ErroInternoException;
import br.com.assembleia.repository.VotoRepository;
import br.com.assembleia.validate.VotoValidate;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static br.com.assembleia.util.MessagesProperties.*;

@Service("VotoService")
public class VotoService {

    Logger logger = LoggerFactory.getLogger(VotoService.class);

    private final VotoRepository votoRepository;
    private final VotoConverter votoConverter;
    private final VotoValidate votoValidate;
    private final PautaService pautaService;
    private final ControleService controleService;
    private final AssociadoClient associadoClient;
    private final ValidadorCpfClient validadorCpfClient;

    public VotoService(
        final VotoRepository votoRepository,
        final VotoConverter votoConverter,
        final VotoValidate votoValidate,
        final PautaService pautaService,
        final ControleService controleService,
        final AssociadoClient associadoClient,
        final ValidadorCpfClient validadorCpfClient) {
        this.votoRepository = votoRepository;
        this.votoConverter = votoConverter;
        this.votoValidate = votoValidate;
        this.pautaService = pautaService;
        this.controleService = controleService;
        this.associadoClient = associadoClient;
        this.validadorCpfClient = validadorCpfClient;
    }

    public String votar(final String idAssociado, final VotoDto votoDTO) {
        votoValidate.validate(votoDTO);
        validaAssociado(idAssociado);
        validaPauta(votoDTO);
        validaControle(idAssociado, votoDTO.getIdPauta());

        try {
            logger.info("Salvando voto: [Pauta] " + votoDTO.getIdPauta() + " [Associado] " + idAssociado);
            votoRepository.save(votoConverter.toModel(votoDTO));
            logger.info("Salvando controle do voto: [Pauta] " + votoDTO.getIdPauta() + " [Associado] " + idAssociado);
            registraControleVoto(idAssociado, votoDTO.getIdPauta());
        } catch (Exception e) {
            logger.error("Erro ao registrar voto: [Pauta] " + votoDTO.getIdPauta() + " [Associado] " + idAssociado);
            throw new ErroInternoException(ERRO_VOTAR);
        }

        return VOTO_REGISTRADO;
    }

    public List<Voto> buscarVotosPorPauta(final String idPauta) {
        logger.info("Buscando votos por pauta: [Pauta] " + idPauta);
        return votoRepository.findByIdPauta(idPauta);
    }

    private void validaAssociado(final String idAssociado) {
        try {
            logger.info("Buscando associado: [Associado] {}", idAssociado);
            Associado associado = associadoClient.buscarAssociado(idAssociado).getBody();
            validaCPF(associado.getCpf());
        } catch (FeignException fe) {
            logger.error("Erro ao buscar associado: [Associado] {}", idAssociado);
            throw new ErroInternoException((fe.status() == 404) ? ASSOCIADO_INVALIDO : ERRO_BUSCAR_ASSOCIADO);
        } catch (Exception e) {
            logger.error("Erro ao buscar/validar associado: [Associado] {}", idAssociado);
            throw new ErroInternoException(ERRO_BUSCAR_ASSOCIADO);
        }

    }

    private void validaCPF(String cpf) {
        ResponseEntity<StatusDto> status = validadorCpfClient.validaCpf(cpf);
        if (!HttpStatus.OK.equals(status.getStatusCode()) || !status.getBody().getStatus().equals("ABLE_TO_VOTE")) {
            logger.error("CPF inválido: [CPF] {}", cpf);
            throw new ErroInternoException(ERRO_BUSCAR_ASSOCIADO);
        }
    }

    private void validaControle(final String idAssociado, final String idPauta) {
        final Optional<Controle> controle = controleService.buscarControle(idAssociado, idPauta);
        if (controle.isPresent()) {
            logger.error("Associado já votou nesta pauta: [Pauta] " + idPauta + " [Associado] " + idAssociado);
            throw new ErroInternoException(ASSOCIADO_JA_VOTOU);
        }
    }

    private void validaPauta(final VotoDto votoDTO) {
        final Optional<Pauta> pauta = pautaService.buscarPauta(votoDTO.getIdPauta());
        if (!pauta.isPresent()) {
            logger.error("Pauta não encontrada: [Pauta] {}", votoDTO.getIdPauta());
            throw new ErroInternoException(PAUTA_NAO_ENCONTRADA);
        }

        if (!pauta.get().getSessao().isAberta()) {
            logger.error("Pauta fechada: [Pauta] {}", votoDTO.getIdPauta());
            throw new ErroInternoException(SESSAO_FECHADA);
        }
    }

    private void registraControleVoto(final String idAssociado, final String idPauta) {
        controleService.registrar(Controle.builder()
                                          .idPauta(idPauta)
                                          .idAssociado(idAssociado)
                                          .build());
    }

}
