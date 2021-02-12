package ru.morozov.order.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqConfig {

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

    @Value("${active-mq.OrderCreated-topic}")
    private String orderCreatedTopic;

    @Value("${active-mq.OrderReady-topic}")
    private String orderReadyTopic;

    @Value("${active-mq.OrderCanceled-topic}")
    private String orderCanceledTopic;

    @Value("${active-mq.OrderDone-exchange}")
    private String orderDoneExchange;

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
