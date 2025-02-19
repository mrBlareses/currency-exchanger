package org.mushroom.currencyexchanger.exception;

public class DaoException extends RuntimeException {
    public DaoException(String massage) {
        super(massage);
    }
}
