package coding_challenge.csv;

import coding_challenge.model.ProcessResult;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


//csv writer needs to be adapted to deal with csv records instead
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

        try (BufferedWriter writer = Files.newBufferedWriter(outputFile, StandardCharsets.UTF_8);
             CSVPrinter csvPrinter = new CSVPrinter(writer,
                     CSVFormat.DEFAULT.builder()
                             .setHeader(OUTPUT_HEADER)
                             .get())) {

            for (ProcessResult result : results) {
              csvPrinter.printRecord(
                      result.rawLine(),
                      result.status().name(),
                      result.riskProfile() == null ? "" : result.riskProfile().name(),
                      result.reason() == null ? "" : result.reason()
              );
            }
        }
    }

}
