package service;

import objects.Stock;
import repository.StockRepository;

import java.util.*;

public class StockService {
    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void showStocksSortedByPrice(List<Stock> stocks) {
        stocks.sort(Comparator.comparing(Stock::getPrice).reversed());
        for (Stock stock : stocks) {
            System.out.println(stock);
        }
    }

    public Stock findStockByTicker(String ticker) {
        for (Stock stock : stockRepository.readStockFile()) {
            if (stock.getTickerName().equalsIgnoreCase(ticker)) {
                return stock;
            }
        }
        return null;
    }

    public Stock findStockByName(String name) {
        for (Stock stock : stockRepository.readStockFile()) {
            if (stock.getName().equalsIgnoreCase(name)) {
                return stock;
            }
        }
        return null;
    }

    public List<Stock> findAllStocksBySector(String sector) {
        List<Stock> sectors = new ArrayList<>();
        for (Stock stock : stockRepository.readStockFile()) {
            if (stock.getSector().equalsIgnoreCase(sector)) {
                sectors.add(stock);
            }
        }
        return sectors;
    }

    public void showAllStocks() {
        System.out.println(" -*- Aktier -*- ");
        System.out.printf("%-9s %-21s %-15s %10s %8s %8s %13s   %-15s   %18s\n",
                "Ticker", "Navn", "Sektor", "Pris", "Valuta", "Rating", "Dividend %", "Marked", "Sidst opdateret");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");

        for (Stock stock : stockRepository.readStockFile()) {
            String currencyString = "N/A";
            if (stock.getCurrency() != null) {
                currencyString = stock.getCurrency().getBaseCurrency();
            }

            String lastUpdatedString = "N/A";
            if (stock.getLastUpdated() != null) {
                lastUpdatedString = stock.getLastUpdated().toString();
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

    public void showSectorDistribution() {
        // Henter alle aktier fra repository
        List<Stock> allStocks = stockRepository.readStockFile();
        // Bruger MAP til at parre (Sektors navn med den samlede pris for aktierne i den sektor)
        Map<String, Double> sectorTotals = new HashMap<>();

        // Hele klubbens investeringer
        double totalInvestement = 0.0;

        System.out.println("=== Klubinvestering fordelt på sektorer (% af samlet værdi) ===");
        for (Stock stock : allStocks) {
            String sector = stock.getSector();
            Double price = stock.getPrice();

            sectorTotals.put(sector, sectorTotals.getOrDefault(sector, 0.0) + price);
            totalInvestement += price;
        }

        for (Map.Entry<String, Double> entry : sectorTotals.entrySet()) {
            String sector = entry.getKey();
            double sectorInvestement = entry.getValue();
            double percentage = (sectorInvestement / totalInvestement) * 100;

            System.out.printf("%-20s: %6.2f%%\n", sector, percentage);
        }
    }
}