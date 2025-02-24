package org.mushroom.currencyexchanger.dao;

import org.mushroom.currencyexchanger.entity.Currency;
import org.mushroom.currencyexchanger.exception.SqlQuarryException;
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
            SELECT * FROM currency_database.public.currencies
            """;

    public static CurrencyDao getInstance() {
        return INSTANCE;
    }

    public List<Currency> findAll() throws SqlQuarryException {

        System.out.println("Получение соединения с БД...");
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet result = statement.executeQuery();
            return extractCurrency(result);

        } catch (SQLException e) {
            throw new SqlQuarryException("Ошибка при выполнении запроса", e);
        }
    }

    private List<Currency> extractCurrency(ResultSet resultSet) throws SQLException {
        List<Currency> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(mapRow(resultSet));
        }
        return result;
    }

    private Currency mapRow(ResultSet resultSet) throws SQLException {
        return new Currency(resultSet.getLong("id"),
                resultSet.getString("code"),
                resultSet.getString("full_name"),
                resultSet.getString("sign")
        );
    }
}


