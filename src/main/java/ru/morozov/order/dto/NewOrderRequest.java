package ru.morozov.order.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NewOrderRequest {
    private Long userId;
    private String deliveryDetails;
}
