package service;

import objects.Stock;
import repository.StockRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StockService {

    public static Stock findByTicker (String ticker) {
        for (Stock stock : StockRepository.readStockFile()) {
            if (stock.getTickerName().equalsIgnoreCase(ticker)) {
                return stock;
            }
        }
        return null;
    }

    public static Stock findByName (String name) {
        for (Stock stock : StockRepository.readStockFile()) {
            if (stock.getName().equalsIgnoreCase(name)) {
                return stock;
            }
        }
        return null;
    }

    public static List<Stock> findAllBySector (String sector) {
        List<Stock> sectors = new ArrayList<>();
        for (Stock stock : StockRepository.readStockFile()) {
            if (stock.getSector().equalsIgnoreCase(sector)) {
                sectors.add(stock);
            }
        }
        return sectors;
    }

    public static void showTop10Stocks (List<Stock> stocks) {
        stocks.sort(Comparator.comparing(Stock::getPrice).reversed());
        int limit = Math.min(stocks.size(), 10);
        for (int i = 0; i < limit; i++) {
            System.out.println(stocks.get(i));
        }
    }
    public static void showWholeStockList (List<Stock> stocks) {
        for (Stock stock : stocks) {
            System.out.println(stocks.get(stocks.size()));
        }
    }

    public static void sellStock () {


    }

    public static void buyStock () {

    }
}