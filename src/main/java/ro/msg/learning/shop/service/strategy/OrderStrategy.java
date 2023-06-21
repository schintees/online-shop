package ro.msg.learning.shop.service.strategy;

import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.model.Stock;

import java.util.List;

public interface OrderStrategy {

    List<Stock> findStocks(List<OrderDetail> orderDetails);

}
