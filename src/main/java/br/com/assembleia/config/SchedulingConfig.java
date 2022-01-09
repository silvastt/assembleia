package br.com.assembleia.config;

import br.com.assembleia.AssembleiaApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SchedulingConfig {
    public static void main(String[] args) {
        SpringApplication.run(AssembleiaApplication.class, args);
    }
}