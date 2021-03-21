package br.com.assembleia.api;

import br.com.assembleia.dto.SessaoDTO;
import br.com.assembleia.service.PautaService;
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

    @PostMapping("/abrir/{idPauta}")
    public ResponseEntity<String> abrirSessao(@PathVariable String idPauta,
                                              @RequestBody SessaoDTO sessao) throws Exception {
        return ResponseEntity.ok(pautaService.abrirSessao(idPauta, sessao));
    }

}
