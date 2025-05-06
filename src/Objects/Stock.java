package Objects;

import java.time.LocalDate;

public class Stock {
    private String tickerName;
    private String name;
    private String sector;
    private double price;
    private Currency currency;
    private double dividendYield;
    private String market;
    private LocalDate lastUpdated;

    //Constructor
    public Stock(String tickerName, String name, String sector, double price, Currency currency, double dividendYield, String market, LocalDate lastUpdated) {
        this.tickerName = tickerName;
        this.name = name;
        this.sector = sector;
        this.price = price;
        this.currency = currency;
        this.dividendYield = dividendYield;
        this.market = market;
        this.lastUpdated = lastUpdated;
    }

    public String getTickerName() {
        return tickerName;
    }

    public void setTickerName(String tickerName) {
        this.tickerName = tickerName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public double getDividendYield() {
        return dividendYield;
    }

    public void setDividendYield(double dividendYield) {
        this.dividendYield = dividendYield;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString() {
        return ("Tickernavn: " + tickerName + "Navn: " + name + "Sektor: " + sector + "Pris: " + price + "Valuta: " + currency + "Udbytte: " + dividendYield + "Marked: " + market + "Sidst updateret: " + lastUpdated);
    }
}
