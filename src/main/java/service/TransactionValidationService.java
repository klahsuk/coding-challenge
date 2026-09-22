package service;

import java.util.Currency;
import java.util.Set;

public class TransactionValidationService {

    private static final Set<Currency> SUPPORTED_CURRENCIES = Set.of(
            Currency.getInstance("EUR"),
            Currency.getInstance("USD")
    );
}
