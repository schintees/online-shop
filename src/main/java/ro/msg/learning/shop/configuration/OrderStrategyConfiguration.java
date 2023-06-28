package ro.msg.learning.shop.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ro.msg.learning.shop.exception.OrderStrategyNotImplementedException;
import ro.msg.learning.shop.service.strategy.ClosestLocationStrategy;
import ro.msg.learning.shop.service.strategy.MostAbundantLocationStrategy;
import ro.msg.learning.shop.service.strategy.OrderStrategy;
import ro.msg.learning.shop.service.strategy.SingleLocationStrategy;

@Configuration
public class OrderStrategyConfiguration {

    @Value("#{'${order.strategy}'.toUpperCase()}")
    private OrderStrategyMode orderStrategyMode;

    @Bean
    public OrderStrategy getOrderStrategy() {
        switch (orderStrategyMode) {
            case SINGLE_LOCATION:
                return new SingleLocationStrategy();
            case MOST_ABUNDANT_LOCATION:
                return new MostAbundantLocationStrategy();
            case CLOSEST_LOCATION:
                return new ClosestLocationStrategy();
            default:
                throw new OrderStrategyNotImplementedException();
        }
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    private enum OrderStrategyMode {
        SINGLE_LOCATION, MOST_ABUNDANT_LOCATION, CLOSEST_LOCATION
    }

}
