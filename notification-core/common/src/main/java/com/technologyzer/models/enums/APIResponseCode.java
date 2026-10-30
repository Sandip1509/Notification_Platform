package com.technologyzer.models.enums;

import com.technologyzer.models.interfaces.ResponseCode;
import lombok.Getter;

@Getter
public enum APIResponseCode implements ResponseCode {

    SUCCESS(200, "Success"),
    BAD_REQUEST(400, "Bad Request"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),
    VALIDATION_ERROR(422, "Validation Failed"),

    MISSING_REQUEST_PARAM(440, "Missing Request Parameter"),
    MISSING_PATH_VARIABLE(441, "Missing Path Variable"),
    MISSING_REQUEST_HEADER(442, "Missing Request Header"),
    ARGUMENT_TYPE_MISMATCH(443, "Argument Type Mismatch"),
    HTTP_MESSAGE_NOT_READABLE(444, "Malformed JSON or Invalid Request Body"),
    CONVERSION_FAILED(447, "Conversion Failed"),

    INTERNAL_ERROR(500, "Internal Server Error"),
    NULL_POINTER(501, "Null Pointer Exception"),
    ILLEGAL_ARGUMENT(502, "Illegal Argument Exception"),
    ILLEGAL_STATE(503, "Illegal StateException");

    private final Integer code;
    private final String message;

    APIResponseCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
