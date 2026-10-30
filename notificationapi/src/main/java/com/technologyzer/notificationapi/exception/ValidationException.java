package com.technologyzer.notificationapi.exception;

public class ValidationException extends RuntimeException implements AbstractException {

    private Integer statusCode;

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Integer statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    @Override
    public Integer getStatusCode() {
        return statusCode;
    }

    @Override
    public String getErrorMessage() {
        return getMessage();
    }
}
