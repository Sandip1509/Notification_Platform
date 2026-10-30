package com.technologyzer.notificationapi.utils;

import com.technologyzer.notificationapi.models.context.NotificationContextHolder;
import org.slf4j.MDC;
import org.springframework.util.ObjectUtils;

import java.util.Calendar;
import java.util.UUID;

import static com.technologyzer.notificationapi.constants.ApplicationConstants.X_REQUEST_ID;

public final class CommonUtils {

    private static final Calendar calendar = Calendar.getInstance();

    public static long getCurrentTimeStamp() {
        return calendar.getTimeInMillis();
    }

    public static boolean isNotEmpty(final Object input) {
        return !ObjectUtils.isEmpty(input);
    }

    public static boolean isEmpty(final Object input) {
        return ObjectUtils.isEmpty(input);
    }

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public static String getCurrentTenantId() {
        return NotificationContextHolder.getContext().tenantId();
    }

    public static String getCurrentTraceId() {
        return MDC.get(X_REQUEST_ID);
    }

}