package ro.msg.learning.shop.mapper;

import ro.msg.learning.shop.dto.OrderDetailDTO;
import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.model.Product;

public class OrderDetailMapper {

    private OrderDetailMapper() {

    }

    public static OrderDetailDTO toDto(OrderDetail orderDetail) {
        return OrderDetailDTO.builder()
                .product(orderDetail.getProduct().getId())
                .quantity(orderDetail.getQuantity())
                .build();
    }

    public static OrderDetail toEntity(OrderDetailDTO orderDetailDTO) {
        return OrderDetail.builder()
                .product(Product.builder().id(orderDetailDTO.getProduct()).build())
                .quantity(orderDetailDTO.getQuantity())
                .build();
    }
}
