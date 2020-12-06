package ru.morozov.order.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @SequenceGenerator(name="orders_gen", sequenceName="orders_id_seq", allocationSize = 1)
    @GeneratedValue(strategy=SEQUENCE, generator="orders_gen")
    private Long id;

    private Long userId;
    private Integer productId;
    private Integer qnt;
    private Float cost;
}
