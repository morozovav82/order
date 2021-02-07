package ru.morozov.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.morozov.messages.OrderCreatedMsg;
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
        newOrderDto.setStatus(Status.ACTIVE.name());

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
}
