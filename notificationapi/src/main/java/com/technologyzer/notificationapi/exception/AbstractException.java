package com.technologyzer.notificationapi.exception;

public interface AbstractException {
    Integer getStatusCode();
    String getErrorMessage();
}
