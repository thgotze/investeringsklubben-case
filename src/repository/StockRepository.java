package repository;

import Objects.Stock;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StockRepository {
    public static void readStockFile() {
        List<Stock> stocks = new ArrayList<>();

        try {
            File stockFile = new File("src/files/stockMarket.csv");
            Scanner scanner = new Scanner(stockFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data1 = line.split(";");

                String ticker = data1[0];
                String name = data1[1];
                String sector = data1[2].trim();
                int price = Integer.parseInt(data1[3].trim());
                String currency = data1[4].trim();
                String rating = data1[5].trim();
                int dividendYield = Integer.parseInt(data1[6].trim());
                String market = data1[7].trim();
                LocalDate lastUpdated = LocalDate.parse(data1[8].trim(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));


            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }

    }
}
