package com.example.demo.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.demo.dto.NotificationDTO;

// NotificationFaçade (fachada), é uma fachada para outros serviços
@Service
public class NotificationFacade {

  private static Logger logger = LoggerFactory.getLogger(NotificationFacade.class.getName());

  private final Map<String, NotificationService> notificationServices;

  public NotificationFacade(
      Map<String, NotificationService> notificationServices) {
    this.notificationServices = notificationServices;
  }

  public void sendNotification(NotificationDTO notification) {

    notification.media().stream()
        .map(media -> media + "-notification")
        .peek(service -> logger.info("Service {} encontrado", service))
        .forEach(service -> {
          if (notificationServices.containsKey(service)) {
            logger.info("Service {} acionado", service);
            notificationServices.get(service)
                .sendNotification(
                    notification.recipient(), notification.title(),
                    notification.body());
          }
        });
  }
}
