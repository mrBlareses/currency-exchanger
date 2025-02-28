package org.mushroom.currencyexchanger.exception;

import java.sql.SQLException;

public class SqlQueryException extends SQLException {
    public SqlQueryException(String massage) {
        super(massage);
    }
  }
