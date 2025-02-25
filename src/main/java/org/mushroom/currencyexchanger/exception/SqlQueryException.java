package org.mushroom.currencyexchanger.exception;

import java.sql.SQLException;

public class SqlQueryException extends RuntimeException {
    public SqlQueryException(String massage, SQLException e) {
        super(massage);
    }
}
