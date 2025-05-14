package objects;

import repository.CurrencyRepository;

import java.time.LocalDate;

public class Currency {
    private String baseCurrency;
    private String quoteCurrency = "DKK";
    private double rate;
    private LocalDate lastUpdated = LocalDate.now();

    // Constructor
    public Currency(String baseCurrency, String quoteCurrency, double rate, LocalDate lastUpdated) {
        this.baseCurrency = baseCurrency;
        this.quoteCurrency = quoteCurrency;
        this.rate = rate;
        this.lastUpdated = lastUpdated;
    }

    public Currency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
        this.rate = getRateFromBaseCurrency(baseCurrency);
    }

    public double getRateFromBaseCurrency(String baseCurrency) {
        CurrencyRepository currencyRepository = new CurrencyRepository();
        for (Currency currency : currencyRepository.readCurrencyFile()) {
            if (currency.getBaseCurrency().equals(baseCurrency)) {
                return currency.getRate();
            }
        }
        return 0.0;
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