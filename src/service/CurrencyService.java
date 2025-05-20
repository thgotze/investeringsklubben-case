package service;

import models.Currency;
import repository.CurrencyRepository;

import java.time.format.DateTimeFormatter;
import java.util.List;

public final class CurrencyService {
    private final CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public void displayAllCurrencies() {
        System.out.println("-*- Valutakurser -*-");
        System.out.printf("%-8s %-8s %-16s \n", "Valuta", "Kurs", "Sidst opdateret");
        System.out.println("----------------------------------");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        for (Currency currency : currencyRepository.readCurrencyFile()) {
            String formattedDate = currency.getLastUpdated().format(formatter);
            System.out.printf("%-8s %-6s %-16s \n", currency.getBaseCurrency(), currency.getRate(), formattedDate);
        }
        System.out.println("-*- Valutakurser -*-");
    }

    public Currency findByBaseCurrency(String baseCurrency) {
        for (Currency currency : currencyRepository.readCurrencyFile()) {
            if (currency.getBaseCurrency().equalsIgnoreCase(baseCurrency)) {
                return currency;
            }
        }
        return null;
    }

    public Currency findByQuoteCurrency(String quoteCurrency) {
        for (Currency currency : currencyRepository.readCurrencyFile()) {
            if (currency.getQuoteCurrency().equalsIgnoreCase(quoteCurrency)) {
                return currency;
            }
        }
        return null;
    }

    public double currencyConverter(Double amount, Currency preferredCurrency) {
        List<Currency> currencies = currencyRepository.readCurrencyFile();


        for (Currency currency : currencies) {
            if (preferredCurrency.getBaseCurrency().equalsIgnoreCase(currency.getBaseCurrency())) {
                return amount * currency.getRate();
            }

        }
        throw new IllegalArgumentException("Konverteringsraten blev ikke fundet for " + preferredCurrency.getBaseCurrency() + "til " + preferredCurrency.getBaseCurrency());
    }
}
