package com.technologyzer.models.enums;

import com.technologyzer.models.interfaces.ResponseCode;
import lombok.Getter;

@Getter
public enum NotificationResponseCode implements ResponseCode {

    NOTIFICATION_TEMPLATE_NOT_FOUND(1001, "Notification template not found"),
    NOTIFICATION_CHANNEL_UNSUPPORTED(1002, "Notification channel unsupported"),
    NOTIFICATION_PROVIDER_UNAVAILABLE(1003, "Upstream notification provider unavailable"),
    NOTIFICATION_RATE_LIMITED(1004, "Notification rate limited"),
    NOTIFICATION_INVALID_RECIPIENT(1005, "Invalid recipient identifier"),
    NOTIFICATION_SEND_FAILED(1006, "Notification send failed"),
    NOTIFICATION_PAYLOAD_INVALID(1007, "Notification payload invalid"),
    NOTIFICATION_RETRY_EXHAUSTED(1008, "Notification retries exhausted"),
    NOTIFICATION_SCHEDULE_CONFLICT(1009, "Notification schedule conflict"),
    NOTIFICATION_PREFERENCE_BLOCKED(1010, "User has blocked this notificationType");

    private final Integer code;
    private final String message;

    NotificationResponseCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
