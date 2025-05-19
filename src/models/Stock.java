package models;

import java.time.LocalDate;

public class Stock {
    private String ticker;
    private String name;
    private String sector;
    private double price;
    private String rating;
    private Currency currency;
    private double dividendYield;
    private String market;
    private LocalDate lastUpdated;

    // Constructor
    public Stock(String ticker, String name, String sector, double price, String rating, Currency currency, double dividendYield, String market, LocalDate lastUpdated) {
        this.ticker = ticker;
        this.name = name;
        this.sector = sector;
        this.price = price;
        this.rating = rating;
        this.currency = currency;
        this.dividendYield = dividendYield;
        this.market = market;
        this.lastUpdated = lastUpdated;
    }

    public String getTickerName() {
        return ticker;
    }

    public String getName() {
        return name;
    }

    public String getSector() {
        return sector;
    }

    public double getPrice() {
        return price;
    }

    public String getRating() {
        return rating;
    }

    public Currency getCurrency() {
        return currency;
    }

    public double getDividendYield() {
        return dividendYield;
    }

    public String getMarket() {
        return market;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

//    @Override
//    public String toString() {
//        return ticker + ;
//    }
}
