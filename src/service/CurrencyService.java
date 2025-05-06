package service;

import Objects.Currency;
import repository.CurrencyRepository;


public class CurrencyService {

    public static Currency findByBaseCurrency(String baseCurrency) {
        for (Currency currency : CurrencyRepository.readCurrencyFile()) {
            if (currency.getBaseCurrency().equalsIgnoreCase(baseCurrency)) {
                return currency;
            }
        }
        return null;
    }

    // TODO: Tilføj valutaomregner
}