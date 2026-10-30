package com.technologyzer.notificationapi.pubsub.fallback;

public interface GenericFallbackPublisher {

    boolean sendNotification(String topic, String message);
}
