package ru.morozov.order.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.morozov.messages.PaymentSuccessfulMsg;
import ru.morozov.order.service.OrderSagaService;

@Component
@Slf4j
@RequiredArgsConstructor
@RabbitListener(queues = "${mq.PaymentSuccessful-topic}")
public class PaymentSuccessfulConsumer {

    private final OrderSagaService orderSagaService;

    @RabbitHandler
    public void receive(PaymentSuccessfulMsg msg) {
        log.info("Received Message: {}", msg.toString());
        try {
            orderSagaService.paymentSuccessful(msg.getOrderId());
        } catch (Exception e) {
            log.error("Failed to save order", e);
        }
    }
}
