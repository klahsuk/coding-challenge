package coding_challenge.exception;

public class TransactionAlreadyPresentException extends RuntimeException{
    public TransactionAlreadyPresentException() {
    }
    public TransactionAlreadyPresentException(String message) {
        super(message);
    }
}
