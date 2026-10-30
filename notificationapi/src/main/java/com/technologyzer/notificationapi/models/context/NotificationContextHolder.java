package com.technologyzer.notificationapi.models.context;

import java.util.UUID;

public final class NotificationContextHolder {
    private static final ThreadLocal<NotificationContext> NOTIFICATION_CONTEXT_THREAD_LOCAL = new ThreadLocal<>();

    public static void setContext(NotificationContext context) {
        NOTIFICATION_CONTEXT_THREAD_LOCAL.set(context);
    }
    public static NotificationContext getContext() {
        return NOTIFICATION_CONTEXT_THREAD_LOCAL.get();
    }

    public static void clear(){
        NOTIFICATION_CONTEXT_THREAD_LOCAL.remove();
    }

    public static void ignoreTenantIdInjection() {
        UUID tenantId = NOTIFICATION_CONTEXT_THREAD_LOCAL.get().tenantId();
        NOTIFICATION_CONTEXT_THREAD_LOCAL.set(new NotificationContext(tenantId, true));
    }

    public static void ignoreTenantIdInjection(final boolean input) {
        UUID tenantId = NOTIFICATION_CONTEXT_THREAD_LOCAL.get().tenantId();
        NOTIFICATION_CONTEXT_THREAD_LOCAL.set(new NotificationContext(tenantId, input));
    }
}
