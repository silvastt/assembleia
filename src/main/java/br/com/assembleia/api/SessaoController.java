package br.com.assembleia.api;

import br.com.assembleia.dto.SessaoDto;
import br.com.assembleia.service.PautaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/assembleia/sessao")
public class SessaoController {

    private final PautaService service;

    public SessaoController(final PautaService service) {
        this.service = service;
    }

    @ApiOperation(value = "Abri uma pauta para votação.", response = String.class)
    @ApiResponses({
            @ApiResponse(code = 400, message = "Erro na requisição"),
            @ApiResponse(code = 422, message = "Erro ao processar dados de entrada"),
            @ApiResponse(code = 500, message = "Erro inesperado.")
    })
    @PostMapping("/abrir/{idPauta}")
    public ResponseEntity<String> abrirSessao(@PathVariable String idPauta,
                                              @RequestBody SessaoDto sessao) {
        return ResponseEntity.ok(service.abrirSessao(idPauta, sessao));
    }

}
