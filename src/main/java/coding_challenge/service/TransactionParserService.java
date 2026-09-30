package coding_challenge.service;

import coding_challenge.model.TransactionCsv;
import coding_challenge.model.TransactionParseResult;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Component
public class TransactionParserService {

    TransactionParseResult parseCsv(CSVRecord record) {

        String id = record.get("payment_id");
        String recipientName = record.get("recipient_name");
        String recipientIban = record.get("recipient_iban");
        String amount = record.get("amount");
        String currency = record.get("currency");
        String paymentReference = record.get("payment_reference");

        try {
            TransactionCsv csv = new TransactionCsv(
                    id,
                    recipientName,
                    recipientIban,
                    amount,
                    currency,
                    paymentReference
            );

            return TransactionParseResult.success(csv);
        } catch (Exception e) {
            return TransactionParseResult.failure(e.getMessage(), record.toString());
        }
    }

}
