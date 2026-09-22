package coding_challenge.model;

import java.util.Optional;

public record TransactionParseResult(
        Optional<TransactionCsv> transactionCsv,
        String errorMessage,
        String rawLine
) {

    public static TransactionParseResult success(TransactionCsv csv) {
        return new TransactionParseResult(Optional.of(csv), null, null);
    }

    public static TransactionParseResult failure(String errorMessage, String rawLine) {
        return new TransactionParseResult(Optional.empty(), errorMessage, rawLine);
    }
}
