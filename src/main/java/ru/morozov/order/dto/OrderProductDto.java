package ru.morozov.order.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderProductDto {
    private Long id;
    private Long productId;
    private Integer quantity;
    private Float price;
}
