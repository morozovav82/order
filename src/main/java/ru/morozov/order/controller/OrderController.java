package ru.morozov.order.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.jodah.expiringmap.ExpiringMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.morozov.order.OrderMapper;
import ru.morozov.order.dto.NewOrderDto;
import ru.morozov.order.dto.OrderDto;
import ru.morozov.order.entity.Order;
import ru.morozov.order.messages.OrderCreatedMsg;
import ru.morozov.order.producer.OrderProducer;
import ru.morozov.order.repo.OrderRepository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RestController()
@RequestMapping("/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;

    private static Map<String, NewOrderDto> idempotenceKeys = ExpiringMap.builder()
            .expiration(1, TimeUnit.MINUTES)
            .build();

    @PostMapping("")
    public ResponseEntity<OrderDto> createOrder(@RequestBody NewOrderDto order, @RequestHeader("X-Request-Id") String idempotenceKey) {
        //idempotence check
        NewOrderDto iOrder = idempotenceKeys.put(idempotenceKey + "_" + order.hashCode(), order);
        if (iOrder != null && iOrder.equals(order)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        //create order
        OrderDto orderDto = OrderMapper.convertOrderToOrderDto(
                orderRepository.save(
                        OrderMapper.convertNewOrderDtoToOrder(order)
                )
        );

        log.info("Order saved. OrderId=" + orderDto.getId());

        //send message to MQ
        orderProducer.sendMessage(new OrderCreatedMsg(orderDto.getId()));

        return new ResponseEntity(orderDto, HttpStatus.CREATED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable("orderId") Long orderId) {
        Optional<Order> res = orderRepository.findById(orderId);
        if (res.isPresent()) {
            return new ResponseEntity(
                    OrderMapper.convertOrderToOrderDto(res.get()),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
