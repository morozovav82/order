package ru.morozov.order.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CartDto {
    private Long id;
    private Long userId;
    private List<CartProductDto> products;
}
