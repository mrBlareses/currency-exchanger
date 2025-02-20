package org.mushroom.currencyexchanger.dao;

import org.mushroom.currencyexchanger.entity.Currency;
import org.mushroom.currencyexchanger.exception.DaoException;
import org.mushroom.currencyexchanger.exception.GetConnactionException;
import org.mushroom.currencyexchanger.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CurrencyDao {
    private static final CurrencyDao INSTANCE = new CurrencyDao();

    private static final String FIND_ALL_SQL = """
            SELECT id, code, full_name, sign  FROM currancy_database.public.currencies
            """;

    public static CurrencyDao getInstance() {
        return INSTANCE;
    }

    public List<Currency> findAll() {

        try {
            Connection connection = ConnectionManager.get();
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL);
            ResultSet result = statement.executeQuery();

            List<Currency> currencies = new ArrayList<>();
            while (result.next()) {
                currencies.add(new Currency(
                        result.getLong("id"),
                        result.getString("code"),
                        result.getString("full_name"),
                        result.getString("sign")));
            }
            System.out.println("Полученные валюты:");
            currencies.forEach(System.out::println);

            return currencies;
        } catch (SQLException e) {
            throw new DaoException("Ошибка при выполнении запроса");
        } catch (GetConnactionException e) {
            throw new RuntimeException("1");
        }
    }
}

