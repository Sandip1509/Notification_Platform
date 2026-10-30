package com.technologyzer.models.interfaces;

public interface GenericAPIResponse<R> {

    boolean isSuccess();

    R getData();

    Integer getCode();

    String getErrorMessage();

}
