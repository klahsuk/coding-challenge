package coding_challenge.model;

import java.util.Optional;

public record TransactionValidationResult(
        Optional<Transaction> transaction, //instead return a TransFail? inheritance
        String errorMessage
) {

    public static TransactionValidationResult success(Transaction tx) {
        return new TransactionValidationResult(Optional.of(tx), null);
    }

    public static TransactionValidationResult failure(String errorMessage) {
        return new TransactionValidationResult(Optional.empty(), errorMessage);
    }
}
