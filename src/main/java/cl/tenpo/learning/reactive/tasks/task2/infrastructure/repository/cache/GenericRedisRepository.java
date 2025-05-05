package cl.tenpo.learning.reactive.tasks.task2.infrastructure.repository.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.function.Predicate;

@Slf4j
@AllArgsConstructor
public class GenericRedisRepository<T> {
    private final String className = this.getClass().getSimpleName();
    private final String appName;
    private final String keyPrefix;
    private final Long ttlSeconds;
    private final Class<T> clazz;
    private final ObjectMapper objectMapper;
    private final ReactiveRedisTemplate<String, Object> reactiveRedisTemplate;

    public Mono<T> find(final String key) {
        return reactiveRedisTemplate.opsForValue()
            .get(fullKey(key))
            .map(obj -> objectMapper.convertValue(obj, clazz));
    }

    public Mono<T> save(final String key, final T value) {
        return reactiveRedisTemplate.opsForValue()
            .set(fullKey(key), value, Duration.ofSeconds(ttlSeconds))
            .thenReturn(value)
            .doOnError(err -> log.warn("[{}] Error saving [{}] with key [{}]", className, clazz.getSimpleName(), key))
            .onErrorResume(err -> Mono.just(value));
    }

    public Mono<Boolean> delete(final String key) {
        return reactiveRedisTemplate.opsForValue()
            .delete(fullKey(key));
    }

    public Mono<Long> deleteAll() {
        String pattern = String.format("%s:%s:*", appName, keyPrefix);
        return reactiveRedisTemplate.keys(pattern)
            .flatMap(this::delete)
            .filter(Predicate.isEqual(Boolean.TRUE))
            .count();
    }

    private String fullKey(final String key) {
        return String.format("%s:%s:%s", appName, keyPrefix, key);
    }
}
