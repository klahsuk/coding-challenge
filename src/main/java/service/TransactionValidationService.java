package service;

import coding_challenge.exception.InvalidTransactionException;
import coding_challenge.model.*;
import org.springframework.stereotype.Service;

import java.util.Currency;
import java.util.Set;

@Service
public class TransactionValidationService {

    private static final Set<Currency> SUPPORTED_CURRENCIES = Set.of(
            Currency.getInstance("EUR"),
            Currency.getInstance("USD")
    );

    TransactionValidationResult validateTransaction(TransactionCsv csv) {
        try {
            long id = Long.parseLong(csv.getId());
            if(id < 0) {
                throw new InvalidTransactionException("Transaction ID cannot be negative : " + id);
            }

            int amountInCents = (int) (Float.parseFloat(csv.getAmount()) * 100);
            if (amountInCents < 0) {
                throw new InvalidTransactionException("Transaction amount cannot be negative : " + amountInCents);
            }

            IBAN recipientIban = new IBAN(csv.getRecipientIban());
            Currency currency = Currency.getInstance(csv.getCurrency());

            if (!SUPPORTED_CURRENCIES.contains(currency)) {
                throw new InvalidTransactionException("Unsupported Currency : " + currency
                        + "\n Supported Currencies: EUR and USD");
            }

            RiskLevel riskLevel = measureRisk(amountInCents);

            Transaction transaction = new Transaction(
                    id,
                    csv.getRecepientName(),
                    recipientIban,
                    amountInCents,
                    currency,
                    csv.getPaymentReference(),
                    riskLevel
            );

            return TransactionValidationResult.success(transaction);
        } catch (InvalidTransactionException | IllegalArgumentException e) {
            return TransactionValidationResult.failure(e.getMessage());
        }

    }

    private RiskLevel measureRisk(int amount) {
        RiskLevel riskLevel = RiskLevel.NORMAL;
        if (amount > 50000 && amount <= 200000) {
            riskLevel = RiskLevel.FORMAL_APPROVAL_REQUIRED;}
        else if (amount > 200000) {
            riskLevel = RiskLevel.HIGH_RISK_REVIEW;
        }

        return riskLevel;
    }
}
