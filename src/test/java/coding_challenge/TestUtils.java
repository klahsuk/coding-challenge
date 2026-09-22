package coding_challenge;

import coding_challenge.model.TransactionCsv;

public class TestUtils {

    public TestUtils() {
    }

    public TransactionCsv transactionCsv() {
        return new TransactionCsv(
                "1000001",
                "Recipient 1000001",
                "DE00000000000000100503",
                "100.25",
                "USD",
                "Invoice 100503"
                );
    }

    public TransactionCsv transactionCsvWithZeroAmount() {
        return new TransactionCsv(
                "1000001",
                "Recipient 1000001",
                "DE00000000000000100503",
                "0.00",
                "USD",
                "Invoice 100503"
        );
    }

    public TransactionCsv transactionCsvInINR() {
        return new TransactionCsv(
                "1000001",
                "Recipient 1000001",
                "DE00000000000000100503",
                "111.00",
                "INR",
                "Invoice 100503"
        );
    }
}
