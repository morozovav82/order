package ru.morozov.messages;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NotEnoughProductMsg implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long orderId;
}
