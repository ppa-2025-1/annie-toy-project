package com.example.demo.model.business;

public interface INotification {

    void send(
        String email,
        String title,
        String body
    );
    
}
