package br.com.assembleia.api;

import br.com.assembleia.dto.VotoDTO;
import br.com.assembleia.service.VotoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/assembleia")
public class VotoRest {

    private final VotoService votoService;

    @Autowired
    public VotoRest(VotoService votoService) {
        this.votoService = votoService;
    }

    @ApiOperation(value = "Registra o voto de um associado", response = String.class)
    @ApiResponses({
            @ApiResponse(code = 400, message = "Erro na requisição"),
            @ApiResponse(code = 422, message = "Erro ao processar dados de entrada"),
            @ApiResponse(code = 500, message = "Erro inesperado.")
    })
    @PostMapping("/votar/{idAssociado}")
    public ResponseEntity<String> votar(@PathVariable String idAssociado,
                                        @RequestBody VotoDTO voto) throws Exception {
        return ResponseEntity.ok(votoService.votar(idAssociado, voto));
    }

}
