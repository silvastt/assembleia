package br.com.assembleia.client;

import br.com.assembleia.dto.StatusDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "validaCpf", url = "${validador-cpf.url:#{null}}")
public interface ValidadorCpfClient {

    @GetMapping("/users/{cpf}")
    ResponseEntity<StatusDto> validaCpf(@PathVariable String cpf);

}

