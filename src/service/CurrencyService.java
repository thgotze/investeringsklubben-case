package service;

import objects.Currency;
import repository.CurrencyRepository;

import java.util.List;

public class CurrencyService {
    public static void showAllCurrencies() {
        System.out.println("-*- Currencies -*-");
        for (Currency currency : CurrencyRepository.readCurrencyFile()) {
            System.out.println(currency.getBaseCurrency() + " Quote currency: " + currency.getQuoteCurrency() +
                    " Exchange rate" + currency.getRate() + " Last updated: " + currency.getLastUpdated());

        }
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
