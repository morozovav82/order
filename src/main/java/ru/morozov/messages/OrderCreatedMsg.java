package ru.morozov.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreatedMsg implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long orderId;
    private Long userId;
}
