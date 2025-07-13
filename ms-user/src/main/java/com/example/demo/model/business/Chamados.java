package com.example.demo.model.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

// COREOGRAFIA DE SERVIÇOS (service choreography)

@Component
public class Chamados implements IChamados {
    // https://docs.spring.io/spring-boot/reference/io/rest-client.html
    // Existem 3 libs: RestTemplate, WebClient, RestClient
    private final RestTemplate rest;

    private static Logger logger = LoggerFactory
        .getLogger(Chamados.class.getName());

    public Chamados(RestTemplate restTemplate) {
        this.rest = restTemplate;
    }

    @Override
    @Async // RODA EM OUTRA THREAD (PODE RODAR EM OUTRO NÚCLEO)
    public void create(String userHandle, int userId) {

        logger.info(
            "Criando o chamado para o usuario {} com o id {userId}",
            userHandle, userId);
        // hardcoded: escrito no próprio código
        // poderia ser resolvindo com uma variável de ambiente
        // ou diretório de serviços (service discovery: Eureka)
        rest.postForEntity("http://localhost:8083/api/v1/chamados/user", new ChamadosRequest(
            userHandle, userId), Void.class);
    }
    
    static record ChamadosRequest (
        String userHandle,
        int userId
    ) {

    }
}
