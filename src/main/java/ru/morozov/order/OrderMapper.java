package ru.morozov.order;

import ru.morozov.order.dto.NewOrderDto;
import ru.morozov.order.dto.OrderDto;
import ru.morozov.order.entity.Order;

public class OrderMapper {

    public static OrderDto convertOrderToOrderDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setUserId(order.getUserId());
        orderDto.setProductId(order.getProductId());
        orderDto.setQnt(order.getQnt());
        orderDto.setCost(order.getCost());

        return orderDto;
    }

    public static Order convertNewOrderDtoToOrder(NewOrderDto newOrderDto) {
        Order order = new Order();
        order.setUserId(newOrderDto.getUserId());
        order.setProductId(newOrderDto.getProductId());
        order.setQnt(newOrderDto.getQnt());
        order.setCost(newOrderDto.getCost());

        return order;
    }

}
