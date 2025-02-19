package org.mushroom.currencyexchanger.jdbc;

import org.mushroom.currencyexchanger.dao.CurrencyDao;
import org.mushroom.currencyexchanger.entity.Currency;

import java.sql.SQLException;
import java.util.List;

public class JdbcRunner {
    public static void main(String[] args) throws SQLException {
        List<Currency> currencies = CurrencyDao.getInstance().findAll();
        currencies.forEach(System.out::println);
    }
}