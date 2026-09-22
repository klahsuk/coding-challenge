package service;

import coding_challenge.csv.CsvReader;
import coding_challenge.model.Transaction;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class Processor {
    private final CsvReader reader;
    private final TransactionParserService transactionParserService;
    private final TransactionValidationService transactionValidationService;
    private final TransactionService transactionService;


    public Processor(
            CsvReader reader,
            TransactionParserService transactionParserService,
            TransactionValidationService transactionValidationService,
            TransactionService transactionService
    ) {
        this.reader = reader;
        this.transactionParserService = transactionParserService;
        this.transactionValidationService = transactionValidationService;
        this.transactionService = transactionService;
    }

    public void process() throws IOException {
        List<Transaction> validTransactions =
        this.reader.readTransactions().stream()
                .distinct()
                .map(transactionParserService::parseCsv)
                .filter(parseRes -> parseRes.transactionCsv().isPresent())
                .map(parseRes -> transactionValidationService.validateTransaction(parseRes.transactionCsv().get()))
                .filter(txRes -> txRes.transaction().isPresent())
                .map(txRes -> txRes.transaction().get())
                .toList();

        this.transactionService.saveAll(validTransactions);
    }
}

