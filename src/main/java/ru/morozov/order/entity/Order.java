package ru.morozov.order.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @SequenceGenerator(name="orders_gen", sequenceName="orders_id_seq", allocationSize = 1)
    @GeneratedValue(strategy=SEQUENCE, generator="orders_gen")
    private Long id;

    @OneToMany(mappedBy="order", cascade = CascadeType.ALL)
    private List<OrderProduct> products = new ArrayList<>();

    private Long userId;
    private String status;
    private String deliveryDetails;

    public Map<Long, Integer> getProductQntList() {
        Map<Long, Integer> productsQnt = new HashMap<>();
        products.stream().forEach(i -> {
            productsQnt.put(i.getProductId(), i.getQuantity());
        });
        return productsQnt;
    }
}
