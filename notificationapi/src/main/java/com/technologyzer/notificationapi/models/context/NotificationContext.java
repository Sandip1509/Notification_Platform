package com.technologyzer.notificationapi.models.context;

public record NotificationContext(String tenantId, boolean ignoreTenantIdInjection) {
}
