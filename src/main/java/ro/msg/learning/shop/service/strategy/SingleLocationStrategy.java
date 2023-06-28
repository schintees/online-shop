package ro.msg.learning.shop.service.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import ro.msg.learning.shop.exception.OutOfStockException;
import ro.msg.learning.shop.model.Order;
import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.service.StockService;

import java.util.*;

public class SingleLocationStrategy implements OrderStrategy {

    @Autowired
    private StockService stockService;

    @Override
    public List<Stock> findStocks(Order order) {
        Map<UUID, List<Stock>> stockLocations = new HashMap<>();
        List<OrderDetail> orderDetails = order.getOrderDetails();

        orderDetails.forEach(orderDetail -> {
            List<Stock> stocks = stockService.getAllByProductAndQuantity(orderDetail.getProduct(), orderDetail.getQuantity());
            if (stocks.isEmpty()) {
                throw new OutOfStockException();
            }
            stocks.forEach(stock ->
                    stockLocations.computeIfAbsent(stock.getLocation().getId(), key -> new ArrayList<>()).add(stock)
            );
        });

        return stockLocations.values().stream().filter(stocks -> orderDetails.size() == stocks.size()).findFirst().orElseThrow(OutOfStockException::new);
    }

}
