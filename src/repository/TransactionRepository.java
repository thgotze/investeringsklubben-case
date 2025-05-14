package repository;

import objects.Currency;
import objects.Transaction;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TransactionRepository {
    //Definere dato formatet i CSV filen
    private static final DateTimeFormatter CSV_DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public List<Transaction> readTransactionFile() {
        List<Transaction> transactions = new ArrayList<>();
        try {
            File transactionsFile = new File("resources/transactions.csv");
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
                    LocalDate transactionDate = LocalDate.parse(data2[2].trim(), CSV_DATE_FORMAT);
                    String ticker = data2[3].trim();
                    double pricePerStock = Double.parseDouble(data2[4].trim().replace(",", "."));
                    Currency currency = new Currency(data2[5].trim());
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

    public void addTransactionToFile(Transaction transaction) {
        List<Transaction> transactions = readTransactionFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("resources/transactions.csv", true))) {
            String formattedDate = transaction.getDate().format(CSV_DATE_FORMAT);
            String formattedPrice = String.valueOf(transaction.getPrice()).replace(".", ",");
            writer.newLine();
            writer.write(
                    transaction.getId() + ";" +
                            transaction.getUserId() + ";" +
                            formattedDate + ";" +
                        transaction.getTicker() + ";" +
                        formattedPrice + ";" +
                        transaction.getCurrency().getBaseCurrency() + ";" +
                        transaction.getOrderType() + ";" +
                        transaction.getQuantity());

            transactions.add(transaction);

        } catch (IOException e) {
            System.out.println("Failed to add transaction: " + e.getMessage());
        }
    }
}