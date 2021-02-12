package ru.morozov.order.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.morozov.messages.*;

@RestController
@RequestMapping("/tests")
@RequiredArgsConstructor
@Slf4j
public class TestController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${active-mq.ProductReserved-topic}")
    private String productReservedTopic;

    @Value("${active-mq.NotEnoughProduct-topic}")
    private String notEnoughProductTopic;

    @Value("${active-mq.PaymentSuccessful-topic}")
    private String paymentSuccessfulTopic;

    @Value("${active-mq.PaymentRejected-topic}")
    private String paymentRejectedTopic;

    @Value("${active-mq.DeliveryScheduled-topic}")
    private String deliveryScheduledTopic;

    @Value("${active-mq.DeliveryRejected-topic}")
    private String deliveryRejectedTopic;

    @Value("${active-mq.OrderDone-exchange}")
    private String orderDoneExchange;

    private void sendMessage(String topic, String routingKey, Object message){
        try{
            log.info("Attempting send message to Topic: "+ topic);
            if (routingKey == null)
                rabbitTemplate.convertAndSend(topic, message);
            else
                rabbitTemplate.convertAndSend(topic, routingKey, message);
            log.info("Message sent: {}", message);
        } catch(Exception e){
            log.error("Failed to send message", e);
        }
    }

    @PostMapping("/sendProductReservedMsg")
    public void sendProductReservedMsg(@RequestBody ProductReservedMsg message) {
        sendMessage(productReservedTopic, null, message);
    }

    @PostMapping("/sendNotEnoughProductMsg")
    public void sendNotEnoughProductMsg(@RequestBody NotEnoughProductMsg message) {
        sendMessage(notEnoughProductTopic, null, message);
    }

    @PostMapping("/sendPaymentSuccessfulMsg")
    public void sendPaymentSuccessfulMsg(@RequestBody PaymentSuccessfulMsg message) {
        sendMessage(paymentSuccessfulTopic, null, message);
    }

    @PostMapping("/sendPaymentRejectedMsg")
    public void sendPaymentRejectedMsg(@RequestBody PaymentRejectedMsg message) {
        sendMessage(paymentRejectedTopic, null, message);
    }

    @PostMapping("/sendDeliveryScheduledMsg")
    public void sendNotEnoughProductMsg(@RequestBody DeliveryScheduledMsg message) {
        sendMessage(deliveryScheduledTopic, null, message);
    }

    @PostMapping("/sendDeliveryRejectedMsg")
    public void sendDeliveryRejectedMsg(@RequestBody DeliveryRejectedMsg message) {
        sendMessage(deliveryRejectedTopic, null, message);
    }

    @PostMapping("/sendOrderDoneMsg")
    public void sendOrderDoneMsg(@RequestBody OrderDoneMsg message) {
        sendMessage(orderDoneExchange, "default", message);
    }
}
