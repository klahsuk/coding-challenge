package coding_challenge.model;

public record TransactionCsv(String id, String recipientName, String recipientIban, String amount, String currency,
                             String paymentReference) {
}
