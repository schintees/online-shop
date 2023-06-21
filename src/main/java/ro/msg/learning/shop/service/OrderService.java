package ro.msg.learning.shop.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.model.Order;
import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.repository.OrderRepository;
import ro.msg.learning.shop.repository.StockRepository;
import ro.msg.learning.shop.service.strategy.OrderStrategy;

import java.util.List;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private OrderStrategy orderStrategy;

    public Order create(Order order) {
        List<Stock> stocks = orderStrategy.findStocks(order.getOrderDetails());

        updateStocks(stocks, order.getOrderDetails(), order);
        order.setShippedFrom(stocks.get(0).getLocation());

        return orderRepository.save(order);
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
