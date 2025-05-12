package service;

import objects.Stock;
import repository.StockRepository;

import java.util.Comparator;
import java.util.List;

public class StockService {

    public static void showStocksSortedByPrice(List<Stock> stocks) {
        stocks.sort(Comparator.comparing(Stock::getPrice).reversed());
        for (Stock stock : stocks) {
            System.out.println(stock);
        }
    }

    public static void showAllStocks() {
        System.out.println(" -*- Aktier -*- ");
        System.out.printf("%-9s %-21s %-15s %10s %8s %8s %13s   %-15s   %18s\n",
                "Ticker", "Navn", "Sektor", "Pris", "Valuta", "Rating", "Dividend %", "Marked", "Sidst opdateret");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");

        for (Stock stock : StockRepository.readStockFile()) {
            String currencyString;
            if (stock.getCurrency() != null) {
                currencyString = stock.getCurrency().getBaseCurrency();
            } else {
                currencyString = "N/A";
            }
            String lastUpdatedString;
            if (stock.getLastUpdated() != null) {
                lastUpdatedString = stock.getLastUpdated().toString();
            } else {
                lastUpdatedString = "N/A";
            }

            System.out.printf("%-9s %-21s %-15s %10.2f %8s %8s %11.2f%%    %-15s %18s\n",
                    stock.getTickerName(),
                    stock.getName(),
                    stock.getSector(),
                    stock.getPrice(),
                    currencyString,
                    stock.getRating(),
                    stock.getDividendYield(),
                    stock.getMarket(),
                    lastUpdatedString);
        }
        System.out.println(" -*- Aktier -*-");
    }
}