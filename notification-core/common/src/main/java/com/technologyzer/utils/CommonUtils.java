package com.technologyzer.utils;

import org.slf4j.MDC;

import static com.technologyzer.constants.ApplicationConstant.CORRELATION_ID;

public final class CommonUtils {

    private CommonUtils() {
    }
    public static String getCurrentRequestId() {
        return MDC.get(CORRELATION_ID);
    }
}
