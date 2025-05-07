package service;

import objects.Currency;
import objects.Stock;
import objects.Transaction;
import objects.User;
import repository.TransactionRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionService {

    public static void createTransaction(Stock stock, User user, Currency currency, String orderType, int quantity) {

        // vi snakker om når man har valgt om man vil købe eller sælge stock om det skal være en
        // boolean eller int eller hvad det skal være
        int transactionId = TransactionRepository.readTransactionFile().getLast().getId() + 1;
        int userId = user.getUserId();
        LocalDate date = LocalDate.now();
        String ticker = stock.getTickerName();
        double price = stock.getPrice();

        Transaction transaction = new Transaction(transactionId, userId, date, ticker, price, currency, orderType, quantity);


//        Transaction(int transactionId, int userId, LocalDate date, String ticker, double price, String currency, String orderType, int quantity) {


        // id = counter bullshit (Ikke user input)
        // user_id = får user id'et på den der er logget ind (Ikke user input)
        // date = genereres localdatetime now (Ikke user input)
        // ticker = det er den stock brugeren vil handle (Ikke user input)
        // price = bliver fundet ud fra stock repository (Ikke user input)
        // currency = det er den valuta de vil betale med (USER INPUT)
        // order_type = alt afhænging af om useren vil købe eller sælge den valgte stock (USER INPUT)
        // quantity = antal af valgt stock useren vil købe eller sælge (USER INPUT)


        // til sidst checkes brugerens saldo om de har nok til at købe stock
    }

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

    // // Valider input
    //    if (stock == null || user == null || currency == null ||
    //        !(orderType.equalsIgnoreCase("BUY") && !(orderType.equalsIgnoreCase("SELL"))) {
    //        throw new IllegalArgumentException("Ugyldigt transaktionsinput");
    //    }
    //
    //    // Beregn transaktionsværdi
    //    double transactionAmount = stock.getPrice() * quantity;
    //    double exchangeRate = currency.getRate(); // Antager at rate er fra currency til basisvaluta
    //
    //    // Konverter til brugerens valuta hvis nødvendigt
    //    if (!stock.getCurrency().equals(currency.getBaseCurrency())) {
    //        transactionAmount *= exchangeRate;
    //    }
    //
    //    // Tjek brugerens saldo ved køb
    //    if (orderType.equalsIgnoreCase("BUY") && user.getBalance() < transactionAmount) {
    //        throw new IllegalStateException("Utilstrækkelig saldo");
    //    }
    //
    //    // Opret transaktion
    //    int transactionId = TransactionRepository.getNextId();
    //    Transaction transaction = new Transaction(
    //        transactionId,
    //        user.getUserId(),
    //        LocalDateTime.now(),
    //        stock.getTickerName(),
    //        stock.getPrice(),
    //        currency.getBaseCurrency(), // Gem basisvalutaen
    //        orderType.toUpperCase(),
    //        quantity
    //    );
    //
    //    // Opdater brugerens saldo
    //    if (orderType.equalsIgnoreCase("BUY")) {
    //        user.setBalance(user.getBalance() - transactionAmount);
    //    } else { // SELL
    //        user.setBalance(user.getBalance() + transactionAmount);
    //    }
    //
    //    // Gem transaktion
    //    TransactionRepository.saveTransaction(transaction);
    //    UserRepository.updateUser(user); // Antager at du har en måde at gemme brugeren på
    //
    //    return transaction;
    //}


}
