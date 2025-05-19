package models;

import java.time.LocalDate;

public final class Stock {
    private final String ticker;
    private final String name;
    private final String sector;
    private final double price;
    private final String rating;
    private final String currency;
    private final double dividendYield;
    private final String market;
    private final LocalDate lastUpdated;

    // Constructor
    public Stock(String ticker, String name, String sector, double price, String rating, String currency, double dividendYield, String market, LocalDate lastUpdated) {
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

    public String getCurrency() {
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
}
