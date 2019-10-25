package com.krontobi.customException;

public class NotFindOrdersException extends Exception {
    public NotFindOrdersException(String message) {
        super(message);
    }
}
