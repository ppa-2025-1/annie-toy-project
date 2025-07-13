package com.example.demo.service;

interface NotificationService {
    
    void sendNotification(String destination,
                          String title,
                          String body);
}
