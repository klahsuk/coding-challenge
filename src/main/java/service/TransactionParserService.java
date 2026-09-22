package service;

import coding_challenge.model.TransactionCsv;
import coding_challenge.model.TransactionParseResult;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class TransactionParserService {

    TransactionParseResult parseCsv(String str) {
        if (str == null || str.isBlank()) {
            return TransactionParseResult.failure("Row not found or was empty", str);
        }

        String[] fields = str.split(",", -1);
        if (fields.length != 6) {
            return TransactionParseResult.failure("Missing columns. Expected 6, got " + fields.length, str);
        }

        if (Arrays.stream(fields).anyMatch(String::isBlank)) {
            return TransactionParseResult.failure("One or more fields are empty", str);
        }

        try {
            TransactionCsv csv = new TransactionCsv(
                    fields[0].trim(), //id
                    fields[1].trim(), //name
                    fields[2].trim(), //iban
                    fields[3].trim(), //amount
                    fields[4].trim(), //currency
                    fields[5].trim()  //reference
            );

            System.out.println(csv);

            return TransactionParseResult.success(csv);
        } catch (Exception e) {
            return TransactionParseResult.failure(e.getMessage(), str);
        }
    }

}
