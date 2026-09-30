package coding_challenge.csv;

import org.apache.commons.csv.CSVParser;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

@Component
public class CsvReader {

    public Iterable<CSVRecord> readTransactions() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("input/payments-to-process.csv");
        if (inputStream == null) {
            throw new IOException("Could not find transactions.csv on the classpath");
        }

        try (InputStream stream = inputStream;
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            String[] headers = {"payment_id", "recipient_name", "recipient_iban", "amount", "currency", "payment_reference"};

            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .setHeader(headers)
                    .setSkipHeaderRecord(true)
                    .get();

            try (CSVParser parser = csvFormat.parse(reader)) {
                return parser.getRecords();
            }
        }

    }

}
