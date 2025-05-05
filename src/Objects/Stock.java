package Objects;

import java.time.LocalDate;

public class Stock {
    private String tickerName;
    private String name;
    private String sector;
    private double price;
    private Currency currency;
    private String dividendYield;
    private String market;
    private LocalDate lastUpdated;

    //Constructor
    public Stock(String tickerName, String name, String sector, double price, Currency currency, String dividendYield, String market, LocalDate lastUpdated) {
        this.tickerName = tickerName;
        this.name = name;
        this.sector = sector;
        this.price = price;
        this.currency = currency;
        this.dividendYield = dividendYield;
        this.market = market;
        this.lastUpdated = lastUpdated;
    }

    
}
