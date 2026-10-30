package com.technologyzer.notificationapi.models.context;

import java.util.UUID;

public record NotificationContext(UUID tenantId, boolean ignoreTenantIdInjection) {
}
