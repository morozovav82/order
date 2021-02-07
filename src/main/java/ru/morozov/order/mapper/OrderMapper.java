package ru.morozov.order.mapper;

import org.springframework.util.CollectionUtils;
import ru.morozov.order.dto.OrderDto;
import ru.morozov.order.dto.NewOrderDto;
import ru.morozov.order.entity.Order;

import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderDto convertOrderToOrderDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setUserId(order.getUserId());
        orderDto.setStatus(order.getStatus());
        orderDto.setDeliveryDetails(order.getDeliveryDetails());

        if (!CollectionUtils.isEmpty(order.getProducts())) {
            orderDto.setProducts(
                order.getProducts().stream().map(i -> OrderProductMapper.convertOrderProductToOrderProductDto(i)).collect(Collectors.toList())
            );
        }

        return orderDto;
    }

    public static Order convertNewOrderDtoToOrder(NewOrderDto newOrderDto) {
        Order order = new Order();
        order.setUserId(newOrderDto.getUserId());
        order.setStatus(newOrderDto.getStatus());
        order.setDeliveryDetails(newOrderDto.getDeliveryDetails());

        if (!CollectionUtils.isEmpty(newOrderDto.getProducts())) {
            order.setProducts(
                    newOrderDto.getProducts().stream().map(i -> OrderProductMapper.convertNewOrderProductDtoToOrderProduct(i, order)).collect(Collectors.toList())
            );
        }

        return order;
    }
}
