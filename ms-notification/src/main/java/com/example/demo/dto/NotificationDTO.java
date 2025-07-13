package com.example.demo.dto;

import java.util.List;

public record NotificationDTO(
    String recipient,
    String title,
    String body,
        // ex.: sms, mail, call, discord,whatsapp
    List<String> media 
) {

}
