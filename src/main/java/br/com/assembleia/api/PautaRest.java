package br.com.assembleia.api;

import br.com.assembleia.bo.Pauta;
import br.com.assembleia.dto.PautaDTO;
import br.com.assembleia.service.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/pauta")
public class PautaRest {

    private final PautaService pautaService;

    @Autowired
    public PautaRest(PautaService pautaService) {
        this.pautaService = pautaService;
    }

    @PostMapping("/")
    public ResponseEntity<Pauta> criarPauta(@RequestBody PautaDTO dto) throws Exception {
        return ResponseEntity.ok(pautaService.criarPauta(dto));
    }

}
