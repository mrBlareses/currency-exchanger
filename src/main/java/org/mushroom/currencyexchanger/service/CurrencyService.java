package org.mushroom.currencyexchanger.service;

import org.mushroom.currencyexchanger.dao.CurrencyDao;
import org.mushroom.currencyexchanger.dto.CurrencyDto;
import org.mushroom.currencyexchanger.dto.NewCurrencyPayload;
import org.mushroom.currencyexchanger.entity.Currency;
import org.mushroom.currencyexchanger.exception.SqlQueryException;

import java.util.List;

public class CurrencyService {
    private final CurrencyDao currencyDao = new CurrencyDao();

    public List<CurrencyDto> getAllCurrencies() throws SqlQueryException {
        List<Currency> currencies = currencyDao.findAll();
        return currencies.stream()
                .map(CurrencyDto::fromCurrency)
                .toList();
    }

    public CurrencyDto createCurrency(NewCurrencyPayload payload) throws SqlQueryException {
        Currency currency = new Currency(
                payload.getCode(),
                payload.getName(),
                payload.getSign());
        Currency saved = currencyDao.save(currency);
        return CurrencyDto.fromCurrency(saved);
    }
}
