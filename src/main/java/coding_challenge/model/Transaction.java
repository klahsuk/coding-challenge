package coding_challenge.model;

import jakarta.persistence.*;

import java.util.Currency;

@Entity
public class Transaction {
    @Id
    private Long id;
    private String recipientName;
    @Embedded
    private IBAN recipientIban;
    private int amountInCents;
    private Currency currency;
    private String paymentReference;
    @Enumerated(EnumType.STRING)
    private RiskLevel riskLevel;

    public Transaction(
            Long id, String recipientName, IBAN recipientIban,
            int amountInCents, Currency currency,
            String paymentReference, RiskLevel riskLevel
    ) {
        this.id = id;
        this.recipientName = recipientName;
        this.recipientIban = recipientIban;
        this.amountInCents = amountInCents;
        this.currency = currency;
        this.paymentReference = paymentReference;
        this.riskLevel = riskLevel;
    }
}
