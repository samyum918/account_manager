package com.acmebank.accountmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ApiBadRequestException.class})
    public ApiErrorResponse apiBadRequestHandling(ApiBadRequestException ex) {
        return new ApiErrorResponse(ex.getMessage());
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler({ApiResourceNotFoundException.class})
    public ApiErrorResponse apiResourceNotFoundHandling(ApiResourceNotFoundException ex) {
        return new ApiErrorResponse(ex.getMessage());
    }
}
