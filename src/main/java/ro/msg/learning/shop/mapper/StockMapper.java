package ro.msg.learning.shop.mapper;

import ro.msg.learning.shop.dto.StockDTO;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.model.Stock;

public class StockMapper {

    public static StockDTO toDto(Stock stock) {
        if (stock == null) {
            return null;
        }
        return StockDTO.builder()
                .productId(stock.getProduct().getId())
                .productName(stock.getProduct().getName())
                .locationId(stock.getLocation().getId())
                .locationName(stock.getLocation().getName())
                .quantity(stock.getQuantity())
                .build();
    }

    public static Stock toEntity(StockDTO stockDTO) {
        if (stockDTO == null) {
            return null;
        }
        return Stock.builder()
                .product(Product.builder().id(stockDTO.getProductId()).name(stockDTO.getProductName()).build())
                .location(Location.builder().id(stockDTO.getLocationId()).name(stockDTO.getLocationName()).build())
                .quantity(stockDTO.getQuantity())
                .build();
    }
}
