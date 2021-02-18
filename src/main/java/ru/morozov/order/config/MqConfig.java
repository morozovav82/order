package ru.morozov.order.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqConfig {

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

    @Value("${mq.OrderCreated-topic}")
    private String orderCreatedTopic;

    @Value("${mq.OrderReady-topic}")
    private String orderReadyTopic;

    @Value("${mq.OrderCanceled-topic}")
    private String orderCanceledTopic;

    @Value("${mq.OrderDone-exchange}")
    private String orderDoneExchange;

    @Value("${mq.SagaReserveProduct-topic}")
    private String sagaReserveProductTopic;

    @Value("${mq.SagaMakePayment-topic}")
    private String sagaMakePaymentTopic;

    @Value("${mq.SagaScheduleDelivery-topic}")
    private String sagaScheduleDeliveryTopic;

    @Value("${mq.SagaReserveProductRollback-topic}")
    private String sagaReserveProductRollbackTopic;

    @Value("${mq.SagaMakePaymentRollback-topic}")
    private String sagaMakePaymentRollbackTopic;

    @Value("${mq.SagaScheduleDeliveryRollback-topic}")
    private String sagaScheduleDeliveryRollbackTopic;

    @Bean
    public Queue productReservedQueue() {
        return new Queue(productReservedTopic);
    }

    @Bean
    public Queue notEnoughProductQueue() {
        return new Queue(notEnoughProductTopic);
    }

    @Bean
    public Queue paymentSuccessfulQueue() {
        return new Queue(paymentSuccessfulTopic);
    }

    @Bean
    public Queue paymentRejectedQueue() {
        return new Queue(paymentRejectedTopic);
    }

    @Bean
    public Queue deliveryScheduledQueue() {
        return new Queue(deliveryScheduledTopic);
    }

    @Bean
    public Queue deliveryRejectedQueue() {
        return new Queue(deliveryRejectedTopic);
    }

    @Bean
    public Queue orderCreatedQueue() {
        return new Queue(orderCreatedTopic);
    }

    @Bean
    public Queue orderReadyQueue() {
        return new Queue(orderReadyTopic);
    }

    @Bean
    public Queue orderCanceledQueue() {
        return new Queue(orderCanceledTopic);
    }

    @Bean
    public Queue sagaReserveProductQueue() {
        return new Queue(sagaReserveProductTopic);
    }

    @Bean
    public Queue sagaMakePaymentQueue() {
        return new Queue(sagaMakePaymentTopic);
    }

    @Bean
    public Queue sagaScheduleDeliveryQueue() {
        return new Queue(sagaScheduleDeliveryTopic);
    }

    @Bean
    public Queue sagaReserveProductRollbackQueue() {
        return new Queue(sagaReserveProductRollbackTopic);
    }

    @Bean
    public Queue sagaMakePaymentRollbackQueue() {
        return new Queue(sagaMakePaymentRollbackTopic);
    }

    @Bean
    public Queue sagaScheduleDeliveryRollbackQueue() {
        return new Queue(sagaScheduleDeliveryRollbackTopic);
    }

    @Bean
    TopicExchange orderDoneExchange() {
        return new TopicExchange(orderDoneExchange);
    }
}
