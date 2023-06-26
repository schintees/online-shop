package ro.msg.learning.shop.exception;

public class AuthenticationTypeNotImplementedException extends RuntimeException {

    public AuthenticationTypeNotImplementedException() {
        super("The provided authentication type is not implemented");
    }
}
