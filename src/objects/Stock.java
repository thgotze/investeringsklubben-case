package objects;

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

    //Constructor
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

    public void setTickerName(String ticker) {
        this.ticker = ticker;
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
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
        return ("Ticker: " + ticker + " Navn: " + name + " Sektor: " + sector + " Pris: " + price + " Rate: " + rating + " Valuta: " + currency.getBaseCurrency() + " Udbytte: " + dividendYield + " Marked: " + market + " Sidst updateret: " + lastUpdated);
    }
}
