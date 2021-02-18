package ru.morozov.order.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.morozov.messages.OrderCanceledMsg;
import ru.morozov.messages.OrderCreatedMsg;
import ru.morozov.messages.OrderDoneMsg;
import ru.morozov.messages.OrderReadyMsg;
import ru.morozov.order.service.MessageService;

@Component
@Slf4j
public class OrderProducer {

    @Autowired
    private MessageService messageService;

    @Value("${mq.OrderCreated-topic}")
    private String orderCreatedTopic;

    @Value("${mq.OrderReady-topic}")
    private String orderReadyTopic;

    @Value("${mq.OrderCanceled-topic}")
    private String orderCanceledTopic;

    @Value("${mq.OrderDone-exchange}")
    private String orderDoneExchange;

    public void sendOrderCreatedMessage(OrderCreatedMsg message){
        messageService.scheduleSentMessage(orderCreatedTopic, null, message, OrderCreatedMsg.class);
    }

    public void sendOrderReadyMessage(OrderReadyMsg message){
        messageService.scheduleSentMessage(orderReadyTopic, null, message, OrderReadyMsg.class);
    }

    public void sendOrderCanceledMessage(OrderCanceledMsg message){
        messageService.scheduleSentMessage(orderCanceledTopic, null, message, OrderCanceledMsg.class);
    }

    public void sendOrderDoneMessage(OrderDoneMsg message) {
        messageService.scheduleSentMessage(orderDoneExchange, "default", message, OrderDoneMsg.class);
    }
}