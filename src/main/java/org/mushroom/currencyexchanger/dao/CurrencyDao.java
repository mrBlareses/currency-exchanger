package org.mushroom.currencyexchanger.dao;

import org.mushroom.currencyexchanger.entity.Currency;
import org.mushroom.currencyexchanger.exception.ConflictRequestException;
import org.mushroom.currencyexchanger.exception.InternalServerException;
import org.mushroom.currencyexchanger.exception.SqlQueryException;
import org.mushroom.currencyexchanger.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.postgresql.core.SqlCommandType.SELECT;

public class CurrencyDao {
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

    public List<Currency> findAll() throws SqlQueryException {
        final String FIND_ALL_SQL = "SELECT * FROM currency_database.public.currencies";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet result = statement.executeQuery();
            return extractCurrency(result);
        } catch (SQLException e) {
            throw new SqlQueryException("Ошибка при выполнении запроса");
        }
    }

    public List<Currency> findById(long id) throws SqlQueryException {
        final String FIND_BI_ID_SQL = """
                SELECT * FROM currency_database.public.currencies
                            WHERE currencies.id = ?
                """;

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BI_ID_SQL)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            return extractCurrency(result);

        } catch (SQLException e) {
            throw new SqlQueryException("Ошибка при выполнении запроса");
        }
    }

    public Currency save(Currency currency) throws SqlQueryException {
        final String ADD_NEW_CURRENCY_SQL = """
                INSERT INTO currency_database.public.currencies (code,full_name,sign) VALUES (?,?,?) RETURNING *
                """;

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection
                     .prepareStatement(ADD_NEW_CURRENCY_SQL, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, currency.getCode());
            statement.setString(2, currency.getFullName());
            statement.setString(3, currency.getSign());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                System.err.println("Предупреждение: INSERT не затронул ни одной строки, возможно, данные уже существуют.");
            }

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return new Currency(
                            resultSet.getLong(1),
                            currency.getCode(),
                            currency.getFullName(),
                            currency.getSign()
                    );
                } else {
                    throw new InternalServerException("Ошибка: невозможно получить сгенерированный ID валюты");
                }
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("duplicate key value")) {
                throw new ConflictRequestException("Ошибка: валюта с кодом " + currency.getCode() + " уже существует");
            }
            throw new SqlQueryException("Ошибка при сохранении валюты");
        }
    }
}


