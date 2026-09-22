package coding_challenge.csv;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class CsvReader {

    public List<String> readTransactions() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("input/payments-to-process.csv");
        if (inputStream == null) {
            throw new IOException("Could not find transactions.csv on the classpath");
        }

        try (InputStream stream = inputStream;
             BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            return reader.lines().toList();
        }
    }

}
