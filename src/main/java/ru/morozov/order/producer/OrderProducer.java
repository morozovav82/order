package ru.morozov.order.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.morozov.messages.*;

@Component
@Slf4j
public class OrderProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${active-mq.OrderCreated-topic}")
    private String orderCreatedTopic;

    @Value("${active-mq.OrderReady-topic}")
    private String orderReadyTopic;

    @Value("${active-mq.OrderCanceled-topic}")
    private String orderCanceledTopic;

    @Value("${active-mq.OrderDone-topic}")
    private String orderDoneTopic;

    @Value("${active-mq.ProductSold-topic}")
    private String productSoldTopic;

    private void sendMessage(String topic, Object message){
        try{
            log.info("Attempting send message to Topic: "+ topic);
            rabbitTemplate.convertAndSend(topic, message);
            log.info("Message sent: {}", message);
        } catch(Exception e){
            log.error("Failed to send message", e);
        }
    }

    public void sendOrderCreatedMessage(OrderCreatedMsg message){
        sendMessage(orderCreatedTopic, message);
    }

    public void sendOrderReadyMessage(OrderReadyMsg message){
        sendMessage(orderReadyTopic, message);
    }

    public void sendOrderCanceledMessage(OrderCanceledMsg message){
        sendMessage(orderCanceledTopic, message);
    }

    public void sendOrderDoneMessage(OrderDoneMsg message) {
        sendMessage(orderDoneTopic, message);
    }

    public void sendProductSoldMessage(ProductSoldMsg message) {
        sendMessage(productSoldTopic, message);
    }
}