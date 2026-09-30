package coding_challenge.controller;

import coding_challenge.exception.TransactionAlreadyPresentException;
import coding_challenge.exception.TransactionNotFoundException;
import coding_challenge.model.Transaction;
import coding_challenge.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Transaction> getTransaction(@PathVariable("id") Long id) {
        Transaction transaction = this.transactionService.getTransaction(id)
                .orElseThrow(() -> new TransactionNotFoundException("transaction not found: " + id));

        return ResponseEntity.ok(transaction);
    }

    @GetMapping
    public List<Transaction> listTransactions() {return this.transactionService.getTransactions();}

    @PutMapping(path = {"id"})
    public ResponseEntity<?> updateTransaction(@PathVariable("id") Long id, @RequestBody Transaction transaction) {
        try {
            transactionService.updateTransaction(id, transaction);
            return ResponseEntity.ok().build();
        } catch (TransactionNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody Transaction transaction){
        try {
            this.transactionService.createTransaction(transaction);
            return ResponseEntity.ok().build();
        } catch (TransactionAlreadyPresentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
