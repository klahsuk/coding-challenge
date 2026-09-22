package coding_challenge.csv;

import coding_challenge.model.ProcessResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Component
public class CsvWriter {
    private static final String OUTPUT_HEADER =
            "payment_id,recipient_name,recipient_iban,amount,currency,payment_reference,status,risk_profile,reason";

    private final Path outputFile;

    public CsvWriter(@Value("${processing.output-file:output/transaction-results.csv}") String outputFile) {
        this.outputFile = Path.of(outputFile);
    }

    public void write(List<ProcessResult> results) throws IOException {
        Path parent = outputFile.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }

        try (BufferedWriter writer = Files.newBufferedWriter(outputFile, StandardCharsets.UTF_8)) {
            writer.write(OUTPUT_HEADER);
            writer.newLine();
            for (ProcessResult result : results) {
                writer.write(result.rawLine());
                writer.write(',');
                writer.write(result.status().name());
                writer.write(',');
                writer.write(result.riskProfile() == null ? "" : result.riskProfile().name());
                writer.write(',');
                writer.write(escape(result.reason()));
                writer.newLine();
            }
        }
    }

    private String escape(String value) {
        if (value == null || value.isEmpty()) {
            return "";
        }
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
}
