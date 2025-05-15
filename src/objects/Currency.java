package objects;

import repository.CurrencyRepository;

import java.time.LocalDate;

public class Currency {
    private String baseCurrency;
    private String quoteCurrency;
    private double rate;
    private LocalDate lastUpdated;

    // Constructor
    public Currency(String baseCurrency, String quoteCurrency, double rate, LocalDate lastUpdated) {
        this.baseCurrency = baseCurrency;
        this.quoteCurrency = quoteCurrency;
        this.rate = rate;
        this.lastUpdated = lastUpdated;
    }

    // Constructor that gets other instance variables from base currency
    public Currency(String baseCurrency) {
        this.baseCurrency = baseCurrency;

        CurrencyRepository currencyRepository = new CurrencyRepository();
        for (Currency currency : currencyRepository.readCurrencyFile()) {
            if (currency.getBaseCurrency().equals(baseCurrency)) {
                this.quoteCurrency = currency.getQuoteCurrency();
                this.rate = currency.getRate();
                this.lastUpdated = currency.getLastUpdated();
            }
        }
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getQuoteCurrency() {
        return quoteCurrency;
    }

    public void setQuoteCurrency(String quoteCurrency) {
        this.quoteCurrency = quoteCurrency;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
    }


    @Override
    public String toString() {
        return "1 " + baseCurrency + " svarer til " + rate + " " + quoteCurrency + ", Sidst updateret: " + lastUpdated;
    }
}