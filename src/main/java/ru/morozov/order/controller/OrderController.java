package ru.morozov.order.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.morozov.order.dto.NewOrderRequest;
import ru.morozov.order.dto.OrderDto;
import ru.morozov.order.entity.Status;
import ru.morozov.order.exceptions.NotFoundException;
import ru.morozov.order.repo.RedisRepository;
import ru.morozov.order.service.OrderSagaService;
import ru.morozov.order.service.OrderService;
import ru.morozov.order.utils.AuthUtils;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final RedisRepository redisRepository;
    private final OrderService orderService;
    private final OrderSagaService orderSagaService;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody NewOrderRequest order, @RequestHeader("X-Request-Id") String idempotenceKey) {
        if (!AuthUtils.getCurrentUserId().equals(order.getUserId())) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        idempotenceKey = "CreateOrder_" + idempotenceKey;
        log.info("idempotenceKey={}", idempotenceKey);

        //idempotence check
        NewOrderRequest iOrder = (NewOrderRequest) redisRepository.find(idempotenceKey);
        if (iOrder != null) {
            log.info("Order has already been processed. IdempotenceKey=" + idempotenceKey);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            redisRepository.add(idempotenceKey, order);
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

    @PostMapping("/{orderId:\\d+}/confirm")
    public ResponseEntity confirm(@PathVariable("orderId") Long orderId) {
        try {
            orderService.confirm(orderId);
            orderSagaService.start(orderId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{orderId:\\d+}/cancel")
    public ResponseEntity cancel(@PathVariable("orderId") Long orderId) {
        try {
            orderService.cancel(orderId);
            orderSagaService.cancel(orderId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{orderId:\\d+}/done")
    public ResponseEntity done(@PathVariable("orderId") Long orderId) {
        try {
            orderService.done(orderId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search")
    public List<OrderDto> search(@RequestParam(required = false) String status) {
        if (!StringUtils.hasText(status)) {
            status = Status.NEW.name();
        }

        return orderService.search(status);
    }
}
