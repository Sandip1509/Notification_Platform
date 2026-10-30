package com.technologyzer.services;

import com.technologyzer.models.dto.ApiResponse;
import com.technologyzer.models.enums.APIResponseCode;
import com.technologyzer.models.interfaces.GenericAPIResponse;
import org.springframework.stereotype.Component;
import com.technologyzer.models.interfaces.ResponseCode;

import static com.technologyzer.utils.CommonUtils.getCurrentRequestId;

@Component
public class ResponseHandler {

    public <T> GenericAPIResponse<T> ok() {
        return ApiResponse.success(APIResponseCode.SUCCESS, getCurrentRequestId());
    }

    public <T> GenericAPIResponse<T> ok(T data) {
        return ApiResponse.success(data, APIResponseCode.SUCCESS, getCurrentRequestId());
    }

    public GenericAPIResponse<Void> ok(ResponseCode responseCode) {
        return ApiResponse.success(responseCode, getCurrentRequestId());
    }

    public <T> GenericAPIResponse<T> ok(T data, ResponseCode responseCode) {
        return ApiResponse.success(data, responseCode, getCurrentRequestId());
    }

    public GenericAPIResponse<Void> failure(ResponseCode responseCode) {
        return ApiResponse.failure(responseCode, getCurrentRequestId());
    }

    public GenericAPIResponse<Void> failure(int code, String message) {
        return ApiResponse.failure(code, message, getCurrentRequestId());
    }

    public GenericAPIResponse<Void> failure(ResponseCode responseCode, String message) {
        return ApiResponse.failure(responseCode.getCode(), message, getCurrentRequestId());
    }

}
