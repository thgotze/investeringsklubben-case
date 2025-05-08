package service;

import objects.Stock;
import repository.StockRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StockService {

    public static Stock findByTicker(String ticker) {
        for (Stock stock : StockRepository.readStockFile()) {
            if (stock.getTickerName().equalsIgnoreCase(ticker)) {
                return stock;
            }
        }
        return null;
    }

    public static Stock findByName(String name) {
        for (Stock stock : StockRepository.readStockFile()) {
            if (stock.getName().equalsIgnoreCase(name)) {
                return stock;
            }
        }
        return null;
    }

    public static List<Stock> findAllBySector(String sector) {
        List<Stock> sectors = new ArrayList<>();
        for (Stock stock : StockRepository.readStockFile()) {
            if (stock.getSector().equalsIgnoreCase(sector)) {
                sectors.add(stock);
            }
        }
        return sectors;
    }

    public static void showTop10Stocks(List<Stock> stocks) {
        stocks.sort(Comparator.comparing(Stock::getPrice).reversed());
        int limit = Math.min(stocks.size(), 10);
        for (int i = 0; i < limit; i++) {
            System.out.println(stocks.get(i));
        }
    }

    public static void showWholeStockList() {
        System.out.println(" -*- Aktier -*- ");
        System.out.printf("%-9s %-21s %-15s %10s %8s %8s %13s   %-15s   %18s\n",
                "Ticker", "Navn", "Sektor", "Pris", "Valuta", "Rating", "Dividend %", "Marked", "Sidst opdateret");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");

        for (Stock stock : StockRepository.readStockFile()) {
            String currencyString;
            if (stock.getCurrency() !=null) {
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