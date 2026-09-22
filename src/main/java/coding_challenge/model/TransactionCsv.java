package coding_challenge.model;

public class TransactionCsv {
    private String id;
    private String recepientName;
    private String recipientIban;
    private String amount;
    private String currency;
    private String paymentReference;

    public TransactionCsv(
            String id, String recepientName,
            String recipientIban, String amount,
            String currency, String paymentReference
    ) {
        this.id = id;
        this.recepientName = recepientName;
        this.recipientIban = recipientIban;
        this.amount = amount;
        this.currency = currency;
        this.paymentReference = paymentReference;
    }

    public String getId() {
        return id;
    }

    public String getRecepientName() {
        return recepientName;
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
