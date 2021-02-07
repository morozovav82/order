package ru.morozov.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewOrderProductDto {
    private Long productId;
    private Integer quantity;
    private Float price;
}
