package ro.msg.learning.shop.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.msg.learning.shop.service.strategy.MostAbundantLocationStrategy;
import ro.msg.learning.shop.service.strategy.OrderStrategy;
import ro.msg.learning.shop.service.strategy.SingleLocationStrategy;

@Configuration
public class OrderStrategyConfiguration {

    @Value("#{'${order.strategy}'.toUpperCase()}")
    private OrderStrategyMode orderStrategyMode;

    @Bean
    public OrderStrategy getOrderStrategy() {
        return OrderStrategyMode.MOST_ABUNDANT_LOCATION == orderStrategyMode ? new MostAbundantLocationStrategy()
                : new SingleLocationStrategy();
    }

    private enum OrderStrategyMode {
        SINGLE_LOCATION, MOST_ABUNDANT_LOCATION
    }

}
