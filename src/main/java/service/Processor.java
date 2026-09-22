package service;

import coding_challenge.csv.CsvReader;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class Processor {
    private final CsvReader reader;
    private final TransactionParserService transactionParserService;

    public Processor(CsvReader reader, TransactionParserService transactionParserService) {
        this.reader = reader;
        this.transactionParserService = transactionParserService;
    }

    public void process() throws IOException {
        this.reader.readTransactions()
                .forEach(System.out::println);
    }
}

