package service;

import Objects.Stock;
import repository.StockRepository;

import java.io.File;
import java.util.ArrayList;
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

}
