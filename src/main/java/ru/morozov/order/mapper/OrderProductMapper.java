package ru.morozov.order.mapper;

import ru.morozov.order.dto.OrderProductDto;
import ru.morozov.order.dto.NewOrderProductDto;
import ru.morozov.order.entity.Order;
import ru.morozov.order.entity.OrderProduct;

public class OrderProductMapper {

    public static OrderProductDto convertOrderProductToOrderProductDto(OrderProduct orderProduct) {
        OrderProductDto orderProductDto = new OrderProductDto();
        orderProductDto.setId(orderProduct.getId());
        orderProductDto.setProductId(orderProduct.getProductId());
        orderProductDto.setQuantity(orderProduct.getQuantity());
        orderProductDto.setPrice(orderProduct.getPrice());

        return orderProductDto;
    }

    public static OrderProduct convertNewOrderProductDtoToOrderProduct(NewOrderProductDto newOrderProductDto, Order order) {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setOrder(order);
        orderProduct.setProductId(newOrderProductDto.getProductId());
        orderProduct.setQuantity(newOrderProductDto.getQuantity());
        orderProduct.setPrice(newOrderProductDto.getPrice());

        return orderProduct;
    }
}
