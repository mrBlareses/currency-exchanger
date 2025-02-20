package org.mushroom.currencyexchanger.exception;


public class DataBaseConnectException extends RuntimeException {

    public DataBaseConnectException(String message, NumberFormatException e) {
        super(message);
    }
}
