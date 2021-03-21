package br.com.assembleia.api;

import br.com.assembleia.dto.VotoDTO;
import br.com.assembleia.service.VotoService;
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

    @PostMapping("/votar/{idAssociado}")
    public ResponseEntity<String> votar(@PathVariable String idAssociado,
                                        @RequestBody VotoDTO voto) throws Exception {
        return ResponseEntity.ok(votoService.votar(idAssociado, voto));
    }

}
