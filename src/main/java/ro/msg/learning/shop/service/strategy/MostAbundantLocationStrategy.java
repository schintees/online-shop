package ro.msg.learning.shop.service.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import ro.msg.learning.shop.exception.OutOfStockException;
import ro.msg.learning.shop.model.Order;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.service.StockService;

import java.util.ArrayList;
import java.util.List;

public class MostAbundantLocationStrategy implements OrderStrategy {

    @Autowired
    private StockService stockService;

    @Override
    public List<Stock> findStocks(Order order) {
        List<Stock> foundStocks = new ArrayList<>();

        order.getOrderDetails().forEach(orderDetail -> {
            List<Stock> stocks = stockService.getAllByProductAndQuantity(orderDetail.getProduct(), orderDetail.getQuantity());
            if (stocks.isEmpty()) {
                throw new OutOfStockException();
            }

            foundStocks.add(stocks.get(0));
        });

        return foundStocks;
    }
}
