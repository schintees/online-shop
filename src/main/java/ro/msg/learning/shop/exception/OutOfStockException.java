package ro.msg.learning.shop.exception;

public class OutOfStockException extends RuntimeException {

    public OutOfStockException() {
        super("There is not enough stock for the products");
    }
}
