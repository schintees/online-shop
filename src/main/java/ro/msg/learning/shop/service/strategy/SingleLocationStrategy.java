package ro.msg.learning.shop.service.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import ro.msg.learning.shop.exception.OutOfStockException;
import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.repository.StockRepository;

import java.util.*;

public class SingleLocationStrategy implements OrderStrategy {

    @Autowired
    private StockRepository stockRepository;

    @Override
    public List<Stock> findStocks(List<OrderDetail> orderDetails) {
        Map<UUID, List<Stock>> stockLocations = new HashMap<>();

        orderDetails.forEach(orderDetail -> {
            List<Stock> stocks = stockRepository.findByProductAndQuantityGreaterThanEqualOrderByQuantityDesc(orderDetail.getProduct(), orderDetail.getQuantity());
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
