package service;

import Objects.Transaction;
import repository.TransactionRepository;

import java.util.ArrayList;
import java.util.List;

public class TransactionService {



    public static Transaction findById (int id) {
        for (Transaction transaction : TransactionRepository.readTransactionFile()) {
            if (transaction.getId() == id) {
                return transaction;
            }
        }
        return null;
    }

    public static List<Transaction> findAllByUserId (int userId) {
        List<Transaction> transactionsByUserId = new ArrayList<>();
        for (Transaction transaction : TransactionRepository.readTransactionFile()) {
            if (transaction.getUserId() == userId) {
                transactionsByUserId.add(transaction);
            }
        }
        return transactionsByUserId;
    }
}
