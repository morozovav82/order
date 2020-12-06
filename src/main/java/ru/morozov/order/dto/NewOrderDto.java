package ru.morozov.order.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NewOrderDto {
    private Long userId;
    private Integer productId;
    private Integer qnt;
    private Float cost;
}
