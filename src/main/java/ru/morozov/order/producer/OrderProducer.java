package ru.morozov.order.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import ru.morozov.messages.OrderCreatedMsg;

@Component
@Slf4j
public class OrderProducer {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${active-mq.OrderCreated-topic}")
    private String orderCreatedTopic;

    private void sendMessage(String topic, Object message){
        try{
            log.info("Attempting send message to Topic: "+ topic);
            jmsTemplate.convertAndSend(topic, message);
            log.info("Message sent: {}", message);
        } catch(Exception e){
            log.error("Failed to send message", e);
        }
    }

    public void sendOrderCreatedMessage(OrderCreatedMsg message){
        sendMessage(orderCreatedTopic, message);
    }
}