package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration // CLIENTE HTTP CONFIGURÁVEL
public class RestTemplateFactory {
    // A --> HTTP(s) --> B
    //         <-------- timeout => tempo de espera
    private static final int CINCO_SEGUNDOS = 5000; // milissegundos

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() {
        SimpleClientHttpRequestFactory clientHttpRequestFactory =
            new SimpleClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(CINCO_SEGUNDOS);
        return clientHttpRequestFactory;
    }

    @Bean
    public RestTemplate restTemplate(
        ClientHttpRequestFactory clientHttpRequestFactory) {
        RestTemplate restTemplate =
            new RestTemplate(clientHttpRequestFactory);
        return restTemplate;
    }
}
