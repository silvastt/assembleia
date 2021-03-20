package br.com.assembleia.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/voto")
public class VotoRest {

    @Autowired
    public VotoRest() {
    }

}
