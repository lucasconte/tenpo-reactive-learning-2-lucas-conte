package cl.tenpo.learning.reactive.tasks.task2.infrastructure.configuration.cache;

import cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.cache.PercentageCache;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.properties.CacheProperties;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.repository.cache.GenericRedisRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.ReactiveRedisTemplate;

import java.util.UUID;

@Configuration
public class CacheConfig {
    @Value("${spring.application.name}")
    private String applicationName;

    public static final UUID PERCENTAGE_CACHE_ID = UUID.fromString("8299e8d2-39c0-4e87-b7dc-57034c821f3e");

    @Bean
    public GenericRedisRepository<PercentageCache> percentageCache(
        final CacheProperties cacheProperties,
        final ObjectMapper objectMapper,
        final ReactiveRedisTemplate<String, Object> template
    ) {
        return new GenericRedisRepository<>(
                applicationName,
                cacheProperties.percentage().keyPrefix(),
                cacheProperties.percentage().ttlSeconds(),
                PercentageCache.class,
                objectMapper,
                template
        );
    }
}
