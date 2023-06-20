package ro.msg.learning.shop.mapper;

import ro.msg.learning.shop.dto.OrderDTO;
import ro.msg.learning.shop.model.Customer;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.Order;

import java.util.stream.Collectors;

public class OrderMapper {

    private OrderMapper() {

    }

    public static OrderDTO toDto(Order order) {
        return OrderDTO.builder()
                .id(order.getId())
                .shippedFrom(order.getShippedFrom().getId())
                .customer(order.getCustomer().getId())
                .createdAt(order.getCreatedAt())
                .address(order.getAddress())
                .orderDetails(order.getOrderDetails().stream().map(OrderDetailMapper::toDto).collect(Collectors.toList()))
                .build();
    }

    public static Order toEntity(OrderDTO orderDTO) {
        return Order.builder()
                .id(orderDTO.getId())
                .shippedFrom(Location.builder().id(orderDTO.getShippedFrom()).build())
                .customer(Customer.builder().id(orderDTO.getCustomer()).build())
                .createdAt(orderDTO.getCreatedAt())
                .address(orderDTO.getAddress())
                .orderDetails(orderDTO.getOrderDetails().stream().map(OrderDetailMapper::toEntity).collect(Collectors.toList()))
                .build();
    }
}
