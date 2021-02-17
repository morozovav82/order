package ru.morozov.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.morozov.messages.*;
import ru.morozov.order.dto.OrderDto;
import ru.morozov.order.entity.OrderSaga;
import ru.morozov.order.entity.SagaStep;
import ru.morozov.order.producer.OrderSagaProducer;
import ru.morozov.order.repo.OrderSagaRepository;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderSagaService {

    private final OrderSagaRepository orderSagaRepository;
    private final OrderSagaProducer orderSagaProducer;
    private final OrderService orderService;

    public void start(Long orderId) {
        Optional<OrderSaga> orderSagaStep = orderSagaRepository.findOneByOrderIdAndStep(orderId, SagaStep.Started.name());
        Assert.isTrue(!orderSagaStep.isPresent(), "Step already done");

        log.info("start, OrderId=" + orderId);

        OrderSaga orderSaga = new OrderSaga();
        orderSaga.setOrderId(orderId);
        orderSaga.setStep(SagaStep.Started.name());
        orderSaga.setEndDt(new Date());
        orderSagaRepository.save(orderSaga);

        OrderDto order = orderService.get(orderId);
        orderSagaProducer.sendSagaReserveProductMessage(new SagaReserveProductMsg(order.getId(),  order.getProductQntList()));
    }

    public void productReserved(Long orderId) {
        Optional<OrderSaga> orderSagaStep = orderSagaRepository.findOneByOrderIdAndStep(orderId, SagaStep.ProductReserved.name());
        Assert.isTrue(!orderSagaStep.isPresent(), "Step already done");

        log.info("productReserved, OrderId=" + orderId);

        OrderSaga orderSaga = new OrderSaga();
        orderSaga.setOrderId(orderId);
        orderSaga.setStep(SagaStep.ProductReserved.name());
        orderSaga.setEndDt(new Date());
        orderSagaRepository.save(orderSaga);

        OrderDto order = orderService.get(orderId);
        orderSagaProducer.sendSagaMakePaymentMessage(new SagaMakePaymentMsg(order.getId(), order.getUserId(), order.getTotalPrice()));
    }

    public void notEnoughProduct(Long orderId) {
        Optional<OrderSaga> orderSagaStep = orderSagaRepository.findOneByOrderIdAndStep(orderId, SagaStep.Done.name());
        Assert.isTrue(!orderSagaStep.isPresent(), "Wrong step");

        log.info("notEnoughProduct, OrderId=" + orderId);
        cancel(orderId);
    }

    public void paymentSuccessful(Long orderId) {
        Optional<OrderSaga> orderSagaStep = orderSagaRepository.findOneByOrderIdAndStep(orderId, SagaStep.PaymentSuccessful.name());
        Assert.isTrue(!orderSagaStep.isPresent(), "Step already done");

        log.info("paymentSuccessful, OrderId=" + orderId);

        OrderSaga orderSaga = new OrderSaga();
        orderSaga.setOrderId(orderId);
        orderSaga.setStep(SagaStep.PaymentSuccessful.name());
        orderSaga.setEndDt(new Date());
        orderSagaRepository.save(orderSaga);

        OrderDto order = orderService.get(orderId);
        orderSagaProducer.sendSagaScheduleDeliveryMessage(new SagaScheduleDeliveryMsg(order.getId(), order.getDeliveryDetails()));
    }

    public void paymentRejected(Long orderId) {
        Optional<OrderSaga> orderSagaStep = orderSagaRepository.findOneByOrderIdAndStep(orderId, SagaStep.Done.name());
        Assert.isTrue(!orderSagaStep.isPresent(), "Wrong step");

        log.info("paymentRejected, OrderId=" + orderId);
        cancel(orderId);
    }

    public void deliveryScheduled(Long orderId) {
        Optional<OrderSaga> orderSagaStep = orderSagaRepository.findOneByOrderIdAndStep(orderId, SagaStep.DeliveryScheduled.name());
        Assert.isTrue(!orderSagaStep.isPresent(), "Step already done");

        log.info("deliveryScheduled, OrderId=" + orderId);

        OrderSaga orderSaga = new OrderSaga();
        orderSaga.setOrderId(orderId);
        orderSaga.setStep(SagaStep.DeliveryScheduled.name());
        orderSaga.setEndDt(new Date());
        orderSagaRepository.save(orderSaga);

        done(orderId);
    }

    public void deliveryRejected(Long orderId) {
        Optional<OrderSaga> orderSagaStep = orderSagaRepository.findOneByOrderIdAndStep(orderId, SagaStep.Done.name());
        Assert.isTrue(!orderSagaStep.isPresent(), "Wrong step");

        log.info("deliveryRejected, OrderId=" + orderId);
        cancel(orderId);
    }

    public void done(Long orderId) {
        Optional<OrderSaga> orderSagaStep = orderSagaRepository.findOneByOrderIdAndStep(orderId, SagaStep.Done.name());
        Assert.isTrue(!orderSagaStep.isPresent(), "Step already done");

        orderSagaStep = orderSagaRepository.findOneByOrderIdAndStep(orderId, SagaStep.Canceled.name());
        Assert.isTrue(!orderSagaStep.isPresent(), "Wrong step");

        log.info("done, OrderId=" + orderId);

        OrderSaga orderSaga = new OrderSaga();
        orderSaga.setOrderId(orderId);
        orderSaga.setStep(SagaStep.Done.name());
        orderSaga.setEndDt(new Date());
        orderSagaRepository.save(orderSaga);

        orderService.ready(orderId);
    }

    public void cancel(Long orderId) {
        Optional<OrderSaga> orderSagaStep = orderSagaRepository.findOneByOrderIdAndStep(orderId, SagaStep.Canceled.name());
        Assert.isTrue(!orderSagaStep.isPresent(), "Step already done");

//        orderSagaStep = orderSagaRepository.findOneByOrderIdAndStep(orderId, SagaStep.Done.name());
//        Assert.isTrue(!orderSagaStep.isPresent(), "Wrong step");

        log.info("cancel, OrderId=" + orderId);

        //rollbacks
        OrderDto order = orderService.get(orderId);

        orderSagaStep = orderSagaRepository.findOneByOrderIdAndStep(orderId, SagaStep.ProductReserved.name());
        if (orderSagaStep.isPresent()) {
            orderSagaProducer.sendSagaReserveProductRollbackMessage(new SagaReserveProductRollbackMsg(orderId, order.getProductQntList()));
        }

        orderSagaStep = orderSagaRepository.findOneByOrderIdAndStep(orderId, SagaStep.PaymentSuccessful.name());
        if (orderSagaStep.isPresent()) {
            orderSagaProducer.sendSagaMakePaymentRollbackMessage(new SagaMakePaymentRollbackMsg(orderId, order.getUserId(), order.getTotalPrice()));
        }

        orderSagaStep = orderSagaRepository.findOneByOrderIdAndStep(orderId, SagaStep.DeliveryScheduled.name());
        if (orderSagaStep.isPresent()) {
            orderSagaProducer.sendSagaScheduleDeliveryRollbackMessage(new SagaScheduleDeliveryRollbackMsg(orderId));
        }

        //cancels
        OrderSaga orderSaga = new OrderSaga();
        orderSaga.setOrderId(orderId);
        orderSaga.setStep(SagaStep.Canceled.name());
        orderSaga.setEndDt(new Date());
        orderSagaRepository.save(orderSaga);

        orderService.cancel(orderId);
    }
}
