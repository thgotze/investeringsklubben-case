package repository;

import Objects.Transaction;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TransactionRepository {

    public static List<Transaction> readTransactionFile() {
        List<Transaction> transactions = new ArrayList<>();
        try {
            File transactionsFile = new File("src/files/transactions.csv");
            Scanner reader = new Scanner(transactionsFile);

            boolean isFirstLine = true;
            while (reader.hasNextLine()) {
                String line = reader.nextLine();

                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] data2 = line.split(";");

                try {
                    int transactionId = Integer.parseInt(data2[0].trim());
                    int userId = Integer.parseInt(data2[1].trim());
                    LocalDate transactionDate = LocalDate.parse(data2[2].trim(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    String ticker = data2[3].trim();
                    double pricePerStock = Double.parseDouble(data2[4].trim().replace(",", "."));
                    String currency = data2[5].trim();
                    String orderType = data2[6].trim();
                    int quantity = Integer.parseInt(data2[7].trim());

                    Transaction transaction = new Transaction(transactionId, userId, transactionDate, ticker, pricePerStock, currency, orderType, quantity);
                    transactions.add(transaction);


                } catch (NumberFormatException e) {
                    System.out.println("Could not read line " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
        return transactions;
    }
}
