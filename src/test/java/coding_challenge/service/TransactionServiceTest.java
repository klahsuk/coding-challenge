package coding_challenge.service;

import coding_challenge.model.Transaction;
import coding_challenge.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository repository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void savesTransactionsWhenTheBatchIsNotEmpty() {
        Transaction transaction = new Transaction();
        List<Transaction> transactions = List.of(transaction);

        transactionService.saveAll(transactions);

        verify(repository).saveAll(transactions);
    }

    @Test
    void doesNotCallRepositoryWhenTheBatchIsEmpty() {
        transactionService.saveAll(List.of());

        verify(repository, never()).saveAll(List.of());
    }
}
