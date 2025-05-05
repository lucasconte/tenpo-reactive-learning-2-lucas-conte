package cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cache")
public record CacheProperties(CacheSettings percentage) {
    public record CacheSettings(String keyPrefix, Long ttlSeconds) {
    }
}
