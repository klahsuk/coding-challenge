package coding_challenge.service;

import coding_challenge.model.TransactionCsv;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class TransactionParserServiceTest {

    @InjectMocks
    private TransactionParserService transactionParserService;

    @Test
    void parsingEmptyRowReturnsFailure() {
        var result = transactionParserService.parseCsv("");

        assertTrue(result.transactionCsv().isEmpty());
        assertEquals("Row not found or was empty", result.errorMessage());
        assertEquals("", result.rawLine());
    }

    @Test
    void parsingValidRowReturnsTransactionWithTrimmedFields() {
        var result = transactionParserService.parseCsv(
                "100291, Recipient 100291, DE00000000000000100291, 322.98, EUR, Invoice 100291");

        assertTrue(result.transactionCsv().isPresent());
        assertEquals(
                new TransactionCsv(
                        "100291",
                        "Recipient 100291",
                        "DE00000000000000100291",
                        "322.98",
                        "EUR",
                        "Invoice 100291"
                ),
                result.transactionCsv().orElseThrow()
        );
    }
}
