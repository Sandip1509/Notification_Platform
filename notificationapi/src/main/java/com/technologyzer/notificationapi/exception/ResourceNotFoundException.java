package com.technologyzer.notificationapi.exception;

public class ResourceNotFoundException extends RuntimeException implements AbstractException {

    private Integer statusCode;

    public ResourceNotFoundException(String message, Integer statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public ResourceNotFoundException(Integer statusCode) {
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
