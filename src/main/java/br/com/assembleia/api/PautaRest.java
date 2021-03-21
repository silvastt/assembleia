package br.com.assembleia.api;

import br.com.assembleia.converter.PautaConverter;
import br.com.assembleia.dto.PautaDTO;
import br.com.assembleia.service.PautaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/assembleia/pauta")
public class PautaRest {

    private final PautaService pautaService;
    private final PautaConverter pautaConverter;

    @Autowired
    public PautaRest(PautaService pautaService,
                     PautaConverter pautaConverter) {
        this.pautaService = pautaService;
        this.pautaConverter = pautaConverter;
    }

    @ApiOperation(value = "Cria uma pauta.", response = PautaDTO.class)
    @ApiResponses({
            @ApiResponse(code = 201, message = "Associado criado com sucesso."),
            @ApiResponse(code = 500, message = "Erro inesperado.")
    })
    @PostMapping("/")
    public ResponseEntity<PautaDTO> criarPauta(@RequestBody PautaDTO dto) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(pautaConverter.toDTO(pautaService.criarPauta(dto)));
    }

}
