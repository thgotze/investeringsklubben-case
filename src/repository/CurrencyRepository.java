package repository;

import models.Currency;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class CurrencyRepository {
    
    public List<Currency> readCurrencyFile() {
        List<Currency> currencies = new ArrayList<>();
        try {
            File currencyFile = new File("resources/currency.csv");
            Scanner reader = new Scanner(currencyFile);

            reader.nextLine(); // Skip header

            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] data = line.split(";");

                try {
                    String baseCurrency = data[0].trim();
                    String quoteCurrency = data[1].trim();
                    double rate = Double.parseDouble(data[2].trim().replace(",", "."));
                    LocalDate lastUpdated = LocalDate.parse(data[3].trim(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));

                    Currency currency = new Currency(baseCurrency, quoteCurrency, rate, lastUpdated);
                    currencies.add(currency);

                } catch (NumberFormatException e) {
                    System.out.println("Kan ikke aflæse: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Filen blev ikke fundet!");
        }
        return currencies;
    }
}