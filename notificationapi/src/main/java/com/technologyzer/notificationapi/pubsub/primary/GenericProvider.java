package com.technologyzer.notificationapi.pubsub.primary;

public interface GenericProvider {
    boolean sendNotification(String topic, String message);
}
