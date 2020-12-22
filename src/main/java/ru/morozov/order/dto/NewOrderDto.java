package ru.morozov.order.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@ToString
public class NewOrderDto {
    private Long userId;
    private Integer productId;
    private Integer qnt;
    private Float cost;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewOrderDto that = (NewOrderDto) o;
        return userId.equals(that.userId) &&
                productId.equals(that.productId) &&
                qnt.equals(that.qnt) &&
                cost.equals(that.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, productId, qnt, cost);
    }
}
