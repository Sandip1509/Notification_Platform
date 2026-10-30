package com.technologyzer.notificationapi.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class ApplicationProperties {

    @Value("${spring.kafka.audit.topic}")
    private String auditTopic;

    @Value("${spring.kafka.ingest.topic}")
    private String ingestTopic;

    @Value("${spring.mongodb.uri}")
    private String mongoConnectionURI;

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapKafkaServers;
}
