package cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "external.learning-reactive")
public record LearningReactiveProperties(EndpointProperties getPercentage) {
}
