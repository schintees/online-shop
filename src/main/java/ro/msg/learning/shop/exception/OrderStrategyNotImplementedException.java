package ro.msg.learning.shop.exception;

public class OrderStrategyNotImplementedException extends RuntimeException {

    public OrderStrategyNotImplementedException() {
        super("The provided order strategy is not implemented");
    }
}
