package coding_challenge.exception;

public class InvalidCurrencyException extends RuntimeException{
    public InvalidCurrencyException(String message) {
        super(message);
    }
}
