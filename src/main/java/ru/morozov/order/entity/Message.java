package ru.morozov.order.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.util.Date;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "order_messages")
@Getter
@Setter
public class Message {

    @Id
    @SequenceGenerator(name="order_messages_gen", sequenceName="order_messages_id_seq", allocationSize = 1)
    @GeneratedValue(strategy=SEQUENCE, generator="order_messages_gen")
    private Long id;

    private Date sent;
    private String topic;
    private String routingKey;
    private String message;
    private String className;
}
