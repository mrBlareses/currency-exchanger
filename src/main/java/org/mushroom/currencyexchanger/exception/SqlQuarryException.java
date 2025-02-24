package org.mushroom.currencyexchanger.exception;

import java.sql.SQLException;

public class SqlQuarryException extends RuntimeException {
    public SqlQuarryException(String massage, SQLException e) {
        super(massage);
    }
}
