package br.com.assembleia.api;

import br.com.assembleia.dto.SessaoDTO;
import br.com.assembleia.service.PautaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/assembleia/sessao")
public class SessaoRest {

    private final PautaService pautaService;

    @Autowired
    public SessaoRest(PautaService pautaService) {
        this.pautaService = pautaService;
    }

    @ApiOperation(value = "Abri uma pauta para votação.", response = String.class)
    @ApiResponses({
            @ApiResponse(code = 400, message = "Erro na requisição"),
            @ApiResponse(code = 422, message = "Erro ao processar dados de entrada"),
            @ApiResponse(code = 500, message = "Erro inesperado.")
    })
    @PostMapping("/abrir/{idPauta}")
    public ResponseEntity<String> abrirSessao(@PathVariable String idPauta,
                                              @RequestBody SessaoDTO sessao) {
        return ResponseEntity.ok(pautaService.abrirSessao(idPauta, sessao));
    }

}
