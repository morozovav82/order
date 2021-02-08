package ru.morozov.order.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.util.Date;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "order_saga")
@Getter
@Setter
public class OrderSaga {

    @Id
    @SequenceGenerator(name="order_saga_gen", sequenceName="order_saga_id_seq", allocationSize = 1)
    @GeneratedValue(strategy=SEQUENCE, generator="order_saga_gen")
    private Long id;

    private Long orderId;
    private String step;
    private Date endDt;
}
