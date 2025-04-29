package br.edu.ifrs.riogrande.loginsystem.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import br.edu.ifrs.riogrande.loginsystem.config.RabbitMQConfig;
import br.edu.ifrs.riogrande.loginsystem.database.NewUserEvent;

@Component
public class NewUserEventListener {

    @RabbitListener(queues = RabbitMQConfig.USER_QUEUE)
    public void newUser(NewUserEvent event) {
        
    }
}