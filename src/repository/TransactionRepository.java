package repository;

import models.Transaction;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class TransactionRepository {

    private static final DateTimeFormatter CSV_DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public List<Transaction> readTransactionFile() {
        List<Transaction> transactions = new ArrayList<>();
        try {
            File transactionsFile = new File("resources/transactions.csv");
            Scanner reader = new Scanner(transactionsFile);

            reader.nextLine(); // Skip header

            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] data2 = line.split(";");

                try {
                    int transactionId = Integer.parseInt(data2[0].trim());
                    int userId = Integer.parseInt(data2[1].trim());
                    LocalDate transactionDate = LocalDate.parse(data2[2].trim(), CSV_DATE_FORMAT);
                    String ticker = data2[3].trim();
                    double pricePerStock = Double.parseDouble(data2[4].trim().replace(",", "."));
                    String currency = data2[5].trim();
                    String orderType = data2[6].trim();
                    int quantity = Integer.parseInt(data2[7].trim());

                    Transaction transaction = new Transaction(transactionId, userId, transactionDate, ticker, pricePerStock, currency, orderType, quantity);
                    transactions.add(transaction);

                } catch (NumberFormatException e) {
                    System.out.println("Kan ikke aflæse: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Filen blev ikke fundet!");
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
                            transaction.getCurrency() + ";" +
                            transaction.getOrderType() + ";" +
                            transaction.getQuantity());

            transactions.add(transaction);

        } catch (IOException e) {
            System.out.println("Failed to add transaction: " + e.getMessage());
        }
    }
}