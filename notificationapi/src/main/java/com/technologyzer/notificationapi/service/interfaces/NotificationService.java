package com.technologyzer.notificationapi.service.interfaces;

import com.technologyzer.notificationapi.models.request.SendNotificationRequest;

public interface NotificationService {
    void sendNotification(SendNotificationRequest request);
}
