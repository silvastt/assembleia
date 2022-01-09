package br.com.assembleia.api;

import br.com.assembleia.converter.PautaConverter;
import br.com.assembleia.dto.PautaDto;
import br.com.assembleia.service.PautaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/assembleia/pauta")
public class PautaController {

    private final PautaService service;
    private final PautaConverter converter;

    public PautaController(
        final PautaService service,
        final PautaConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @ApiOperation(value = "Cria uma pauta.", response = PautaDto.class)
    @ApiResponses({
            @ApiResponse(code = 201, message = "Associado criado com sucesso."),
            @ApiResponse(code = 500, message = "Erro inesperado.")
    })
    @PostMapping("/")
    public ResponseEntity<PautaDto> criarPauta(@RequestBody PautaDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(converter.toDTO(service.criarPauta(dto)));
    }

}
