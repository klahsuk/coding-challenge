package coding_challenge.model;

public class TransactionCsv {
    private String id;
    private String recipientName;
    private String recipientIban;
    private String amount;
    private String currency;
    private String paymentReference;

    public TransactionCsv(
            String id, String recipientName,
            String recipientIban, String amount,
            String currency, String paymentReference
    ) {
        this.id = id;
        this.recipientName = recipientName;
        this.recipientIban = recipientIban;
        this.amount = amount;
        this.currency = currency;
        this.paymentReference = paymentReference;
    }

    public String getId() {
        return id;
    }

    public String getRecepientName() {
        return recipientName;
    }

    public String getRecipientIban() {
        return recipientIban;
    }

    public String getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getPaymentReference() {
        return paymentReference;
    }
}
