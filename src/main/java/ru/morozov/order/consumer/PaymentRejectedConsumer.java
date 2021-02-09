package ru.morozov.order.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.morozov.messages.PaymentRejectedMsg;
import ru.morozov.order.service.OrderSagaService;

@Component
@Slf4j
@RequiredArgsConstructor
@RabbitListener(queues = "${active-mq.PaymentRejected-topic}")
public class PaymentRejectedConsumer {

    private final OrderSagaService orderSagaService;

    @RabbitHandler
    public void receive(PaymentRejectedMsg msg) {
        log.info("Received Message: {}", msg.toString());
        try {
            orderSagaService.paymentRejected(msg.getOrderId());
        } catch (Exception e) {
            log.error("Failed to save order", e);
        }
    }
}
