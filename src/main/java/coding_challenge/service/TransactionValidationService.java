package coding_challenge.service;

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
            long id = Long.parseLong(csv.id());
            if(id < 0) {
                throw new InvalidTransactionException("Transaction ID cannot be negative : " + id);
            }

            int amountInCents = (int) (Float.parseFloat(csv.amount()) * 100);
            if (amountInCents <= 0) {
                throw new InvalidTransactionException("Transaction amount has to be more than 0 : " + amountInCents);
            }

            IBAN recipientIban = new IBAN(csv.recipientIban());
            Currency currency = Currency.getInstance(csv.currency());

            if (!SUPPORTED_CURRENCIES.contains(currency)) {
                throw new InvalidTransactionException("Unsupported Currency : " + currency
                        + "\n Supported Currencies: EUR and USD");
            }

            RiskLevel riskLevel = measureRisk(amountInCents);

            Transaction transaction = new Transaction(
                    id,
                    csv.recipientName(),
                    recipientIban,
                    amountInCents,
                    currency,
                    csv.paymentReference(),
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
