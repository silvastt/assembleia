package br.com.assembleia.api;

import br.com.assembleia.dto.ResultadoDTO;
import br.com.assembleia.service.ResultadoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/assembleia/resultado")
public class ResultadoRest {

    private final ResultadoService resultadoService;

    @Autowired
    public ResultadoRest(ResultadoService resultadoService) {
        this.resultadoService = resultadoService;
    }

    @ApiOperation(value = "Gera resultado de uma pauta por ID.", response = ResultadoDTO.class)
    @ApiResponses({
            @ApiResponse(code = 400, message = "Erro na requisição"),
            @ApiResponse(code = 422, message = "Erro ao processar dados de entrada"),
            @ApiResponse(code = 500, message = "Erro inesperado.")
    })
    @GetMapping("/{idPauta}")
    public ResponseEntity<ResultadoDTO> gerarResultado(@PathVariable String idPauta) {
        return ResponseEntity.ok(resultadoService.gerarResultado(idPauta));
    }

}
