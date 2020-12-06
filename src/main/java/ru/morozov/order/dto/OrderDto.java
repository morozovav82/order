package ru.morozov.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto extends NewOrderDto {
    private Long id;
}
