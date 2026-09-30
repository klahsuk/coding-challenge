package coding_challenge.service;

import coding_challenge.model.Transaction;
import coding_challenge.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public void saveAll(List<Transaction> transactions) {
        if(!transactions.isEmpty()){
            this.repository.saveAll(transactions);
        } else {
            System.out.println("no transactions left to be saved");
        }
    }

    public Transaction createTransaction(Transaction transaction) {
        return repository.save(transaction);
    }

    public List<Transaction> getTransactions() {
        return repository.findAll();
    }

    public Optional<Transaction> getTransaction(Long id) {
        return this.repository.findById(id);
    }

    public Optional<Transaction> updateTransaction(Long id, Transaction transaction) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }

        return Optional.of(repository.save(transaction));
    }

    public boolean deleteTransaction(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }

        repository.deleteById(id);
        return true;
    }

    public void deleteTransactions() {
        repository.deleteAll();
    }
}
