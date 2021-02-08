package ru.morozov.order.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
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
    private JmsTemplate jmsTemplate;

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

    private void sendMessage(String topic, Object message){
        try{
            log.info("Attempting send message to Topic: "+ topic);
            jmsTemplate.convertAndSend(topic, message);
            log.info("Message sent: {}", message);
        } catch(Exception e){
            log.error("Failed to send message", e);
        }
    }

    @PostMapping("/sendProductReservedMsg")
    public void sendProductReservedMsg(@RequestBody ProductReservedMsg message) {
        sendMessage(productReservedTopic, message);
    }

    @PostMapping("/sendNotEnoughProductMsg")
    public void sendNotEnoughProductMsg(@RequestBody NotEnoughProductMsg message) {
        sendMessage(notEnoughProductTopic, message);
    }

    @PostMapping("/sendPaymentSuccessfulMsg")
    public void sendPaymentSuccessfulMsg(@RequestBody PaymentSuccessfulMsg message) {
        sendMessage(paymentSuccessfulTopic, message);
    }

    @PostMapping("/sendPaymentRejectedMsg")
    public void sendPaymentRejectedMsg(@RequestBody PaymentRejectedMsg message) {
        sendMessage(paymentRejectedTopic, message);
    }

    @PostMapping("/sendDeliveryScheduledMsg")
    public void sendNotEnoughProductMsg(@RequestBody DeliveryScheduledMsg message) {
        sendMessage(deliveryScheduledTopic, message);
    }

    @PostMapping("/sendDeliveryRejectedMsg")
    public void sendDeliveryRejectedMsg(@RequestBody DeliveryRejectedMsg message) {
        sendMessage(deliveryRejectedTopic, message);
    }
}
