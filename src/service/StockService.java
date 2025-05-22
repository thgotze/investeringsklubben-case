package service;

import models.Stock;
import repository.StockRepository;

import java.util.*;

public final class StockService {
    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }


    public Stock findStockByTicker(String ticker) {
        for (Stock stock : stockRepository.readStockFile()) {
            if (stock.getTickerName().equalsIgnoreCase(ticker)) {
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

    public void displayAllStocks() {
        System.out.println("-*- Aktier -*-");
        System.out.printf("%-9s %-21s %-15s %10s %8s %8s %13s   %-15s   %18s\n",
                "Ticker", "Navn", "Sektor", "Pris", "Valuta", "Rating", "Dividend %", "Marked", "Sidst opdateret");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");

        for (Stock stock : stockRepository.readStockFile()) {
            System.out.printf("%-9s %-21s %-15s %10.2f %8s %8s %11.2f%%    %-15s %18s\n",
                    stock.getTickerName(),
                    stock.getName(),
                    stock.getSector(),
                    stock.getPrice(),
                    stock.getCurrency(),
                    stock.getRating(),
                    stock.getDividendYield(),
                    stock.getMarket(),
                    stock.getLastUpdated());
        }
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("-*- Aktier -*-");
    }

    public void showSectorDistribution() {
        List<Stock> allStocks = stockRepository.readStockFile();
        Map<String, Double> sectorTotals = new HashMap<>();

        // Hele klubbens investeringer
        double totalInvestment = 0.0;

        System.out.println("-*-*- Klubinvestering sektorfordeling -*-*-");
        System.out.println("-------------------------------------------");
        for (Stock stock : allStocks) {
            String sector = stock.getSector();
            Double price = stock.getPrice();

            sectorTotals.put(sector, sectorTotals.getOrDefault(sector, 0.0) + price);
            totalInvestment += price;
        }

        for (Map.Entry<String, Double> entry : sectorTotals.entrySet()) {
            String sector = entry.getKey();
            double sectorInvestment = entry.getValue();
            double percentage = (sectorInvestment / totalInvestment) * 100;

            System.out.printf("%-15s %6.2f%%\n", sector, percentage);
        }
        System.out.println("-------------------------------------------");
        System.out.println("-*-*- Klubinvestering sektorfordeling -*-*-");
    }
}