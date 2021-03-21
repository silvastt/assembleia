package br.com.assembleia.api;

import br.com.assembleia.dto.ResultadoDTO;
import br.com.assembleia.service.ResultadoService;
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

    @GetMapping("/{idPauta}")
    public ResponseEntity<ResultadoDTO> gerarResultado(@PathVariable String idPauta) {
        return ResponseEntity.ok(resultadoService.gerarResultado(idPauta));
    }

}
