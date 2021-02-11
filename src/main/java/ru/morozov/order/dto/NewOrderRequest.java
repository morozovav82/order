package ru.morozov.order.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class NewOrderRequest implements Serializable {
    private Long userId;
    private String deliveryDetails;
}
