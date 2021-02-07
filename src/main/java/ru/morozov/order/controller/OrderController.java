package ru.morozov.order.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.jodah.expiringmap.ExpiringMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.morozov.order.dto.NewOrderRequest;
import ru.morozov.order.dto.OrderDto;
import ru.morozov.order.exceptions.NotFoundException;
import ru.morozov.order.service.OrderService;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    private static Map<String, NewOrderRequest> idempotenceKeys = ExpiringMap.builder()
            .expiration(1, TimeUnit.MINUTES)
            .build();

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody NewOrderRequest order, @RequestHeader("X-Request-Id") String idempotenceKey) {
        log.info("idempotenceKey={}", idempotenceKey);

        //idempotence check
        NewOrderRequest iOrder = idempotenceKeys.put(idempotenceKey, order);
        if (iOrder != null) {
            log.info("Order has already been processed. IdempotenceKey=" + idempotenceKey);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity(orderService.create(order), HttpStatus.CREATED);
    }

    @GetMapping("/{orderId:\\d+}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable("orderId") Long orderId) {
        try {
            return new ResponseEntity(orderService.get(orderId), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
