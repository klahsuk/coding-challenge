package coding_challenge.service;

import coding_challenge.csv.CsvReader;
import coding_challenge.csv.CsvWriter;
import coding_challenge.model.ProcessResult;
import coding_challenge.model.Transaction;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class Processor {
    private final CsvReader reader;
    private final TransactionParserService transactionParserService;
    private final TransactionValidationService transactionValidationService;
    private final TransactionService transactionService;
    private final CsvWriter writer;


    public Processor(
            CsvReader reader,
            TransactionParserService transactionParserService,
            TransactionValidationService transactionValidationService,
            TransactionService transactionService,
            CsvWriter writer
    ) {
        this.reader = reader;
        this.transactionParserService = transactionParserService;
        this.transactionValidationService = transactionValidationService;
        this.transactionService = transactionService;
        this.writer = writer;
    }

    public void process() throws IOException {
        List<Transaction> validTransactions = new ArrayList<>();
        List<ProcessResult> results = new ArrayList<>();

        for (String line : reader.readTransactions()) {
            if (line.isBlank() || line.equalsIgnoreCase(
                    "payment_id,recipient_name,recipient_iban,amount,currency,payment_reference")) {
                continue;
            }

            var parseResult = transactionParserService.parseCsv(line);
            if (parseResult.transactionCsv().isEmpty()) {
                results.add(ProcessResult.failure(line, parseResult.errorMessage()));
                continue;
            }

            var validationResult =
                    transactionValidationService.validateTransaction(parseResult.transactionCsv().get());
            if (validationResult.transaction().isEmpty()) {
                results.add(ProcessResult.failure(line, validationResult.errorMessage()));
                continue;
            }

            Transaction transaction = validationResult.transaction().get();
            validTransactions.add(transaction);
            results.add(ProcessResult.success(line, transaction.getRiskLevel()));
        }

        transactionService.saveAll(validTransactions);
        writer.write(results);
    }
}
