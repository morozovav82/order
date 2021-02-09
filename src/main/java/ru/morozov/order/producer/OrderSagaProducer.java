package ru.morozov.order.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.morozov.messages.*;

@Component
@Slf4j
public class OrderSagaProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${active-mq.SagaReserveProduct-topic}")
    private String sagaReserveProductTopic;

    @Value("${active-mq.SagaMakePayment-topic}")
    private String sagaMakePaymentTopic;

    @Value("${active-mq.SagaScheduleDelivery-topic}")
    private String sagaScheduleDeliveryTopic;

    @Value("${active-mq.SagaReserveProductRollback-topic}")
    private String sagaReserveProductRollbackTopic;

    @Value("${active-mq.SagaMakePaymentRollback-topic}")
    private String sagaMakePaymentRollbackTopic;

    @Value("${active-mq.SagaScheduleDeliveryRollback-topic}")
    private String sagaScheduleDeliveryRollbackTopic;

    private void sendMessage(String topic, Object message){
        try{
            log.info("Attempting send message to Topic: "+ topic);
            rabbitTemplate.convertAndSend(topic, message);
            log.info("Message sent: {}", message);
        } catch(Exception e){
            log.error("Failed to send message", e);
        }
    }

    public void sendSagaReserveProductMessage(SagaReserveProductMsg message){
        sendMessage(sagaReserveProductTopic, message);
    }

    public void sendSagaMakePaymentMessage(SagaMakePaymentMsg message){
        sendMessage(sagaMakePaymentTopic, message);
    }

    public void sendSagaScheduleDeliveryMessage(SagaScheduleDeliveryMsg message){
        sendMessage(sagaScheduleDeliveryTopic, message);
    }

    public void sendSagaReserveProductRollbackMessage(SagaReserveProductRollbackMsg message){
        sendMessage(sagaReserveProductRollbackTopic, message);
    }

    public void sendSagaMakePaymentRollbackMessage(SagaMakePaymentRollbackMsg message){
        sendMessage(sagaMakePaymentRollbackTopic, message);
    }

    public void sendSagaScheduleDeliveryRollbackMessage(SagaScheduleDeliveryRollbackMsg message){
        sendMessage(sagaScheduleDeliveryRollbackTopic, message);
    }
}