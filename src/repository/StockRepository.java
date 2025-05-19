package repository;

import models.Stock;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class StockRepository {

    public List<Stock> readStockFile() {
        List<Stock> stocks = new ArrayList<>();
        try {
            File stockFile = new File("resources/stockMarket.csv");
            Scanner reader = new Scanner(stockFile);

            reader.nextLine(); // Skip header

            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] data1 = line.split(";");

                try {
                    String ticker = data1[0];
                    String name = data1[1];
                    String sector = data1[2].trim();
                    double price = Double.parseDouble(data1[3].trim().replace(",", "."));
                    String currency = data1[4].trim();
                    String rating = data1[5].trim();
                    double dividendYield = Double.parseDouble(data1[6].trim().replace(",", "."));
                    String market = data1[7].trim();
                    LocalDate lastUpdated = LocalDate.parse(data1[8].trim(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));

                    Stock stock = new Stock(ticker, name, sector, price, rating, currency, dividendYield, market, lastUpdated);
                    stocks.add(stock);

                } catch (NumberFormatException e) {
                    System.out.println("Kan ikke aflæse: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Filen blev ikke fundet!");
        }
        return stocks;
    }
}