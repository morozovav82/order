package ru.morozov.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class OrderDto {
    private Long id;
    private Long userId;
    private List<OrderProductDto> products;
    private String deliveryDetails;
    private String status;

    @JsonIgnore
    public Map<Long, Integer> getProductQntList() {
        Map<Long, Integer> productsQnt = new HashMap<>();
        products.stream().forEach(i -> {
            productsQnt.put(i.getProductId(), i.getQuantity());
        });
        return productsQnt;
    }

    public Float getTotalPrice() {
        return products.stream().map(i -> i.getPrice()).reduce((i, j) -> i + j).get();
    }
}
