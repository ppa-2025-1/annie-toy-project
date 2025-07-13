package com.example.demo.model.business;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

// COREOGRAFIA DE SERVIÇOS (service choreography)

@Component
public class Notification implements INotification {
    // https://docs.spring.io/spring-boot/reference/io/rest-client.html
    // Existem 3 libs: RestTemplate, WebClient, RestClient
    private final RestTemplate rest;

    private static Logger logger = LoggerFactory
        .getLogger(Notification.class.getName());

    public Notification(RestTemplate restTemplate) {
        this.rest = restTemplate;
    }

    @Override
    @Async // RODA EM OUTRA THREAD (PODE RODAR EM OUTRO NÚCLEO)
    public void send(String email, String title, String body) {

        logger.info(
            "Enviando email para {} com o título {}",
            email, title);
        // hardcoded: escrito no próprio código
        // poderia ser resolvindo com uma variável de ambiente
        // ou diretório de serviços (service discovery: Eureka)
        rest.postForEntity("http://localhost:8082/api/v1/notification", new NotificationRequest(
            email, title, body, List.of("email")), Void.class);
    }
    
    static record NotificationRequest (
        String recipient,
        String title,
        String body,
        List<String> media
    ) {

    }
}
