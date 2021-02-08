package ru.morozov.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.morozov.messages.*;
import ru.morozov.order.dto.*;
import ru.morozov.order.entity.Order;
import ru.morozov.order.entity.Status;
import ru.morozov.order.exceptions.NotFoundException;
import ru.morozov.order.mapper.OrderMapper;
import ru.morozov.order.producer.OrderProducer;
import ru.morozov.order.repo.OrderRepository;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;
    private final CartService cartService;

    public OrderDto create(NewOrderRequest order) {
        //get cart info
        CartDto cartDto = cartService.getCart(order.getUserId());

        NewOrderDto newOrderDto = new NewOrderDto();
        newOrderDto.setUserId(order.getUserId());
        newOrderDto.setDeliveryDetails(order.getDeliveryDetails());
        newOrderDto.setStatus(Status.NEW.name());

        newOrderDto.setProducts(cartDto.getProducts().stream()
                .map(i -> {
                    NewOrderProductDto newOrderProductDto = new NewOrderProductDto();
                    newOrderProductDto.setProductId(i.getProductId());
                    newOrderProductDto.setQuantity(i.getQuantity());
                    newOrderProductDto.setPrice(i.getPrice());
                    return newOrderProductDto;
                })
                .collect(Collectors.toList()));

        //create order
        OrderDto orderDto = OrderMapper.convertOrderToOrderDto(
                orderRepository.save(
                        OrderMapper.convertNewOrderDtoToOrder(newOrderDto)
                )
        );

        log.info("Order saved. OrderId=" + orderDto.getId());

        //send message to MQ
        orderProducer.sendOrderCreatedMessage(new OrderCreatedMsg(orderDto.getId(), order.getUserId()));

        return orderDto;
    }

    public OrderDto get(Long orderId) {
        Optional<Order> res = orderRepository.findById(orderId);
        if (res.isPresent()) {
            return OrderMapper.convertOrderToOrderDto(res.get());
        } else {
            throw new NotFoundException(orderId);
        }
    }

    public void confirm(Long orderId) {
        Optional<Order> res = orderRepository.findOneByIdAndStatus(orderId, Status.NEW.name());
        if (res.isPresent()) {
            Order order = res.get();
            order.setStatus(Status.CONFIRMED.name());
            orderRepository.save(order);
        } else {
            throw new NotFoundException(orderId);
        }
    }

    public void ready(Long orderId) {
        Optional<Order> res = orderRepository.findOneByIdAndStatus(orderId, Status.CONFIRMED.name());
        if (res.isPresent()) {
            Order order = res.get();
            order.setStatus(Status.READY.name());
            orderRepository.save(order);

            orderProducer.sendOrderReadyMessage(new OrderReadyMsg(orderId));
        } else {
            throw new NotFoundException(orderId);
        }
    }

    public void done(Long orderId) {
        Optional<Order> res = orderRepository.findOneByIdAndStatus(orderId, Status.READY.name());
        if (res.isPresent()) {
            Order order = res.get();
            order.setStatus(Status.DONE.name());
            orderRepository.save(order);

            orderProducer.sendOrderDoneMessage(new OrderDoneMsg(orderId));
            orderProducer.sendProductSoldMessage(new ProductSoldMsg(order.getProductQntList()));
        } else {
            throw new NotFoundException(orderId);
        }
    }

    public void cancel(Long orderId) {
        Optional<Order> res = orderRepository.findById(orderId);
        if (res.isPresent()) {
            Order order = res.get();
            order.setStatus(Status.CANCELED.name());
            orderRepository.save(order);

            orderProducer.sendOrderCanceledMessage(new OrderCanceledMsg(orderId));
        } else {
            throw new NotFoundException(orderId);
        }
    }
}
