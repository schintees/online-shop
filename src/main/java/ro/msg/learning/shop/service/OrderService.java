package ro.msg.learning.shop.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.exception.OutOfStockException;
import ro.msg.learning.shop.model.Order;
import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.repository.OrderRepository;
import ro.msg.learning.shop.repository.StockRepository;

import java.util.*;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StockRepository stockRepository;

    public Order create(Order order) {
        var stocksOptional = findStocks(order);
        if (stocksOptional.isEmpty()) {
            throw new OutOfStockException();
        }

        List<Stock> stocks = stocksOptional.get();
        updateStocks(stocks, order.getOrderDetails(), order);
        order.setShippedFrom(stocks.get(0).getLocation());

        return orderRepository.save(order);
    }

    private Optional<List<Stock>> findStocks(Order order) {
        Map<UUID, List<Stock>> stockLocations = new HashMap<>();

        order.getOrderDetails().forEach(orderDetail -> {
            List<Stock> stocks = stockRepository.findByProductAndQuantityGreaterThanEqual(orderDetail.getProduct(), orderDetail.getQuantity());
            if (stocks.isEmpty()) {
                throw new OutOfStockException();
            }
            stocks.forEach(stock ->
                    stockLocations.computeIfAbsent(stock.getLocation().getId(), key -> new ArrayList<>()).add(stock)
            );
        });

        return stockLocations.values().stream().filter(stocks -> order.getOrderDetails().size() == stocks.size()).findFirst();
    }

    private void updateStocks(List<Stock> stocks, List<OrderDetail> orderDetails, Order order) {
        stocks.forEach(stock ->
                orderDetails.stream().filter(orderDetail -> stock.getProduct().getId().equals(orderDetail.getProduct().getId())).findFirst()
                        .ifPresent(orderDetail -> {
                            stock.setQuantity(stock.getQuantity() - orderDetail.getQuantity());
                            stockRepository.save(stock);
                            orderDetail.setOrder(order);
                        })
        );
    }

}