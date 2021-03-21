package br.com.assembleia.service;

import br.com.assembleia.bo.Voto;
import br.com.assembleia.converter.PautaConverter;
import br.com.assembleia.dto.ItemVotoDTO;
import br.com.assembleia.dto.ResultadoDTO;
import br.com.assembleia.dto.VotoEnum;
import br.com.assembleia.error.ErroInternoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service("ResultadoService")
public class ResultadoService {

    Logger logger = LoggerFactory.getLogger(ResultadoService.class);

    private static final String ERRO_GERAR_RELATORIO = "Erro ao gerar relatório!";

    private final PautaService pautaService;
    private final PautaConverter pautaConverter;
    private final VotoService votoService;

    @Autowired
    public ResultadoService(PautaService pautaService,
                            PautaConverter pautaConverter,
                            VotoService votoService) {
        this.pautaService = pautaService;
        this.pautaConverter = pautaConverter;
        this.votoService = votoService;
    }

    public ResultadoDTO gerarResultado(String idPauta) {
        try {
            logger.info("Gerando resultado: [Pauta] " + idPauta);
            return ResultadoDTO.builder().pauta(pautaConverter.toDTO(pautaService.buscarPauta(idPauta).get()))
                               .votos(calculaVotos(idPauta))
                               .build();
        } catch (Exception e) {
            logger.error("Erro ao gerar relatório: [Pauta] " + idPauta);
            throw new ErroInternoException(ERRO_GERAR_RELATORIO);
        }
    }

    private List<ItemVotoDTO> calculaVotos(String idPauta) {
        logger.info("Iniciando calculo de votos: [Pauta] " + idPauta);
        List<ItemVotoDTO> lista = new ArrayList<>();
        List<Voto> votos = votoService.buscarVotosPorPauta(idPauta);
        logger.info("Buscando respostas: [Pauta] " + idPauta);
        List<VotoEnum> listaEnumVotos = Arrays.asList(VotoEnum.values());

        logger.info("Gerando calculo: [Pauta] " + idPauta);
        listaEnumVotos.forEach(e -> carregaVotos(e, votos, lista));

        return lista;
    }

    private void carregaVotos(VotoEnum e, List<Voto> votos, List<ItemVotoDTO> lista) {
        logger.info("Calculando votos: [Pauta] " + e);
        Long quantidadeVotos = votos.stream().filter(f -> f.getVoto().getDescricao().equals(e.getDescricao())).count();
        lista.add(ItemVotoDTO.builder()
                             .item(e)
                             .quantidadeVotos(quantidadeVotos.intValue()).build());
    }
}
