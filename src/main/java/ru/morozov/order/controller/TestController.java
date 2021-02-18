package ru.morozov.order.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.morozov.messages.*;
import ru.morozov.order.service.MessageService;

@RestController
@RequestMapping("/tests")
@RequiredArgsConstructor
@Slf4j
public class TestController {

    @Autowired
    private MessageService messageService;

    @Value("${mq.ProductReserved-topic}")
    private String productReservedTopic;

    @Value("${mq.NotEnoughProduct-topic}")
    private String notEnoughProductTopic;

    @Value("${mq.PaymentSuccessful-topic}")
    private String paymentSuccessfulTopic;

    @Value("${mq.PaymentRejected-topic}")
    private String paymentRejectedTopic;

    @Value("${mq.DeliveryScheduled-topic}")
    private String deliveryScheduledTopic;

    @Value("${mq.DeliveryRejected-topic}")
    private String deliveryRejectedTopic;

    @Value("${mq.OrderDone-exchange}")
    private String orderDoneExchange;

    @PostMapping("/sendProductReservedMsg")
    public void sendProductReservedMsg(@RequestBody ProductReservedMsg message) {
        messageService.scheduleSentMessage(productReservedTopic, null, message, ProductReservedMsg.class);
    }

    @PostMapping("/sendNotEnoughProductMsg")
    public void sendNotEnoughProductMsg(@RequestBody NotEnoughProductMsg message) {
        messageService.scheduleSentMessage(notEnoughProductTopic, null, message, NotEnoughProductMsg.class);
    }

    @PostMapping("/sendPaymentSuccessfulMsg")
    public void sendPaymentSuccessfulMsg(@RequestBody PaymentSuccessfulMsg message) {
        messageService.scheduleSentMessage(paymentSuccessfulTopic, null, message, PaymentSuccessfulMsg.class);
    }

    @PostMapping("/sendPaymentRejectedMsg")
    public void sendPaymentRejectedMsg(@RequestBody PaymentRejectedMsg message) {
        messageService.scheduleSentMessage(paymentRejectedTopic, null, message, PaymentRejectedMsg.class);
    }

    @PostMapping("/sendDeliveryScheduledMsg")
    public void sendNotEnoughProductMsg(@RequestBody DeliveryScheduledMsg message) {
        messageService.scheduleSentMessage(deliveryScheduledTopic, null, message, DeliveryScheduledMsg.class);
    }

    @PostMapping("/sendDeliveryRejectedMsg")
    public void sendDeliveryRejectedMsg(@RequestBody DeliveryRejectedMsg message) {
        messageService.scheduleSentMessage(deliveryRejectedTopic, null, message, DeliveryRejectedMsg.class);
    }

    @PostMapping("/sendOrderDoneMsg")
    public void sendOrderDoneMsg(@RequestBody OrderDoneMsg message) {
        messageService.scheduleSentMessage(orderDoneExchange, "default", message, OrderDoneMsg.class);
    }
}
