package ru.morozov.order.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class NewOrderDto {
    private Long userId;
    private List<NewOrderProductDto> products;
    private String deliveryDetails;
    private String status;
}
