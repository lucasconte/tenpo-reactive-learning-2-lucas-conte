package cl.tenpo.learning.reactive.tasks.task2.domain.port;

import reactor.core.publisher.Mono;

public interface CachePort<T> {
    Mono<T> find(String key);

    Mono<T> save(String key, T value);

    Mono<Boolean> delete(String key);

    Mono<Long> deleteAll();
}
