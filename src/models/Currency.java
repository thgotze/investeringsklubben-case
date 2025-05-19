package models;

import java.time.LocalDate;

public final class Currency {
    private final String baseCurrency;
    private final String quoteCurrency;
    private final double rate;
    private final LocalDate lastUpdated;

    // Constructor
    public Currency(String baseCurrency, String quoteCurrency, double rate, LocalDate lastUpdated) {
        this.baseCurrency = baseCurrency;
        this.quoteCurrency = quoteCurrency;
        this.rate = rate;
        this.lastUpdated = lastUpdated;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public String getQuoteCurrency() {
        return quoteCurrency;
    }

    public double getRate() {
        return rate;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    @Override
    public String toString() {
        return "1 " + baseCurrency + " svarer til " + rate + " " + quoteCurrency + ", Sidst updateret: " + lastUpdated;
    }
}