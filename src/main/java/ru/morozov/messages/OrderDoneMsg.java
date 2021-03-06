package ru.morozov.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderDoneMsg implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long orderId;
    private Map<Long, Integer> productsQnt; //ProductId -> Qnt
}