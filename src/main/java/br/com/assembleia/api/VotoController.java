package br.com.assembleia.api;

import br.com.assembleia.dto.VotoDto;
import br.com.assembleia.service.VotoService;
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
@RequestMapping(value = "/assembleia")
public class VotoController {

    private final VotoService service;

    public VotoController(final VotoService service) {
        this.service = service;
    }

    @ApiOperation(value = "Registra o voto de um associado", response = String.class)
    @ApiResponses({
            @ApiResponse(code = 400, message = "Erro na requisição"),
            @ApiResponse(code = 422, message = "Erro ao processar dados de entrada"),
            @ApiResponse(code = 500, message = "Erro inesperado.")
    })
    @PostMapping("/votar/{idAssociado}")
    public ResponseEntity<String> votar(@PathVariable String idAssociado,
                                        @RequestBody VotoDto voto) {
        return ResponseEntity.ok(service.votar(idAssociado, voto));
    }

}
