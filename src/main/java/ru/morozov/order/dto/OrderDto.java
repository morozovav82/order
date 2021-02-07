package ru.morozov.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDto {
    private Long id;
    private Long userId;
    private List<OrderProductDto> products;
    private String deliveryDetails;
    private String status;
}
