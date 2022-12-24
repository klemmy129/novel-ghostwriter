package com.klemmy.novel.ghostwriter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "message-bus")
public record MessageBusProperties(
    String type,
    String brokerUrl,
    String topic
) {
}
