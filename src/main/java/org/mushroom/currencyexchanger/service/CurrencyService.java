package org.mushroom.currencyexchanger.service;

import org.mushroom.currencyexchanger.dao.CurrencyDao;
import org.mushroom.currencyexchanger.dto.CurrencyDto;
import org.mushroom.currencyexchanger.entity.Currency;

import java.util.List;

public class CurrencyService {
    private final CurrencyDao currencyDao = new CurrencyDao();

    public List<CurrencyDto> getAllCurrencies() {
        List<Currency> currencies = currencyDao.findAll();
        return currencies.stream()
                .map(CurrencyDto::fromCurrency)
                .toList();
    }
}
