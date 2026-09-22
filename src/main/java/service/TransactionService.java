package service;

import coding_challenge.model.Transaction;
import coding_challenge.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
