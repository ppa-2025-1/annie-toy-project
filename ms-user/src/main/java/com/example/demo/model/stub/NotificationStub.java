package com.example.demo.model.stub;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.demo.model.business.INotification;


public class NotificationStub implements INotification {

    private final Logger logger = LoggerFactory.getLogger(NotificationStub.class.getName());

    @Override
    public void send(String email, String title, String body) {
        logger.info("Enviando email para {} - {} - {}",
            email, title, body);

    }
    
}
