package coding_challenge.service;

import coding_challenge.TestUtils;
import coding_challenge.model.RiskLevel;
import coding_challenge.model.TransactionCsv;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TransactionValidationServiceTest {
    @InjectMocks
    private TransactionValidationService transactionValidationService;

    TestUtils utils = new TestUtils();

    @Test
    void parsingValidTransactionCsvReturnsTransactionValidtaionResult(){
        TransactionCsv csv = utils.transactionCsv();
        var result = transactionValidationService.validateTransaction(csv);
        assertTrue(result.transaction().isPresent());
        assertEquals(RiskLevel.NORMAL, result.transaction().get().getRiskLevel());
    }

    @Test
    void parsingZeroTransactionCsvReturnsInvalidTransactionException(){
        TransactionCsv csv = utils.transactionCsvWithZeroAmount();
        var result = transactionValidationService.validateTransaction(csv);
        assertTrue(result.transaction().isEmpty());
        assertEquals("Transaction amount has to be more than 0 : 0", result.errorMessage());
    }

    @Test
    void parsingAnotherCurrencyFailsWithCurrencyException(){
        TransactionCsv csv = utils.transactionCsvInINR();
        var result = transactionValidationService.validateTransaction(csv);
        assertTrue(result.transaction().isEmpty());
        assertEquals("Unsupported Currency : " + csv.currency()
                + "\n Supported Currencies: EUR and USD", result.errorMessage());
    }
}

