package com.acmebank.accountmanager.exception;

public class ApiBadRequestException extends RuntimeException {
    public ApiBadRequestException(String message) {
        super(message);
    }
}
