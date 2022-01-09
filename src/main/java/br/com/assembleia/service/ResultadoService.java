package br.com.assembleia.service;

import br.com.assembleia.bo.Voto;
import br.com.assembleia.converter.PautaConverter;
import br.com.assembleia.dto.ItemVotoDto;
import br.com.assembleia.dto.ResultadoDto;
import br.com.assembleia.dto.VotoEnum;
import br.com.assembleia.error.ErroInternoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static br.com.assembleia.util.MessagesProperties.ERRO_GERAR_RELATORIO;

@Service("ResultadoService")
public class ResultadoService {

    Logger logger = LoggerFactory.getLogger(ResultadoService.class);

    private final PautaService pautaService;
    private final PautaConverter pautaConverter;
    private final VotoService votoService;

    public ResultadoService(
        final PautaService pautaService,
        final PautaConverter pautaConverter,
        final VotoService votoService) {
        this.pautaService = pautaService;
        this.pautaConverter = pautaConverter;
        this.votoService = votoService;
    }

    public ResultadoDto gerarResultado(final String idPauta) {
        try {
            logger.info("Gerando resultado: [Pauta] {}", idPauta);
            return ResultadoDto.builder().pauta(pautaConverter.toDTO(pautaService.buscarPauta(idPauta).get()))
                               .votos(calculaVotos(idPauta))
                               .build();
        } catch (Exception e) {
            logger.error("Erro ao gerar relatório: [Pauta] {}", idPauta);
            throw new ErroInternoException(ERRO_GERAR_RELATORIO);
        }
    }

    private List<ItemVotoDto> calculaVotos(final String idPauta) {
        logger.info("Iniciando calculo de votos: [Pauta] {}", idPauta);
        List<ItemVotoDto> lista = new ArrayList<>();
        List<Voto> votos = votoService.buscarVotosPorPauta(idPauta);
        logger.info("Buscando respostas: [Pauta] {}", idPauta);
        List<VotoEnum> listaEnumVotos = Arrays.asList(VotoEnum.values());

        logger.info("Gerando calculo: [Pauta] {}", idPauta);
        listaEnumVotos.forEach(e -> carregaVotos(e, votos, lista));

        return lista;
    }

    private void carregaVotos(final VotoEnum e, final List<Voto> votos, final List<ItemVotoDto> lista) {
        logger.info("Calculando votos: [Pauta] {}", e);
        Long quantidadeVotos = votos.stream().filter(f -> f.getVoto().getDescricao().equals(e.getDescricao())).count();
        lista.add(ItemVotoDto.builder()
                             .item(e)
                             .quantidadeVotos(quantidadeVotos.intValue()).build());
    }
}
