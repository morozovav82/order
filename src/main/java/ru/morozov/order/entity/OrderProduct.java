package ru.morozov.order.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "order_products")
@Getter
@Setter
@ToString(exclude = "order")
public class OrderProduct {

    @Id
    @SequenceGenerator(name="order_products_gen", sequenceName="order_products_id_seq", allocationSize = 1)
    @GeneratedValue(strategy=SEQUENCE, generator="order_products_gen")
    private Long id;

    @ManyToOne
    @JoinColumn(name="order_id", nullable=false)
    private Order order;

    private Long productId;
    private Integer quantity;
    private Float price;
}
