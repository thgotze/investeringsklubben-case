package service;

import objects.Currency;
import repository.CurrencyRepository;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class CurrencyService {
    public static void showAllCurrencies() {
        System.out.println("-*- Valutakurser -*-");
        System.out.printf("%-8s %-8s %-16s \n", "Valuta", "Kurs", "Sidst opdateret");
        System.out.println("----------------------------------");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        for (Currency currency : CurrencyRepository.readCurrencyFile()) {
            String formattedDate = currency.getLastUpdated().format(formatter);
            System.out.printf("%-8s %-6s %-16s \n", currency.getBaseCurrency(), currency.getRate(), formattedDate);
        }
        System.out.println("-*- Valutakurser -*-");
    }

    public static Currency findByBaseCurrency(String baseCurrency) {
        for (Currency currency : CurrencyRepository.readCurrencyFile()) {
            if (currency.getBaseCurrency().equalsIgnoreCase(baseCurrency)) {
                return currency;
            }
        }
        return null;
    }

    public static Currency findByQuoteCurrency(String quoteCurrency) {
        for (Currency currency : CurrencyRepository.readCurrencyFile()) {
            if (currency.getQuoteCurrency().equalsIgnoreCase(quoteCurrency)) {
                return currency;
            }
        }
        return null;
    }

    public static double currencyConverter(Double amount, Currency preferredCurrency) {
        List<Currency> currencies = CurrencyRepository.readCurrencyFile();


        for (Currency currency : currencies) {
            if (preferredCurrency.getBaseCurrency().equalsIgnoreCase(currency.getBaseCurrency())) {
                return amount * currency.getRate();
            }

        }
        throw new IllegalArgumentException("Konverteringsraten blev ikke fundet for " + preferredCurrency.getBaseCurrency() + "til " + preferredCurrency.getBaseCurrency());
    }
}
