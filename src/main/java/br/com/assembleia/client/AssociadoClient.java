package br.com.assembleia.client;

import br.com.assembleia.bo.Associado;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "associado", url = "${associado.url:#{null}}")
public interface AssociadoClient {

    @GetMapping("/associado/{id}")
    ResponseEntity<Associado> buscarAssociado(@PathVariable String id);

}

