package ro.msg.learning.shop.service.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import ro.msg.learning.shop.exception.OutOfStockException;
import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.repository.StockRepository;

import java.util.ArrayList;
import java.util.List;

public class MostAbundantLocationStrategy implements OrderStrategy {

    @Autowired
    private StockRepository stockRepository;

    @Override
    public List<Stock> findStocks(List<OrderDetail> orderDetails) {
        List<Stock> stockLocations = new ArrayList<>();

        orderDetails.forEach(orderDetail -> {
            List<Stock> stocks = stockRepository.findByProductAndQuantityGreaterThanEqualOrderByQuantityDesc(orderDetail.getProduct(), orderDetail.getQuantity());
            if (stocks.isEmpty()) {
                throw new OutOfStockException();
            }

            stockLocations.add(stocks.get(0));
        });

        return stockLocations;
    }
}
