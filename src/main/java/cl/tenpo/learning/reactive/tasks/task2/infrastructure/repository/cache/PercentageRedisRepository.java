package cl.tenpo.learning.reactive.tasks.task2.infrastructure.repository.cache;

import cl.tenpo.learning.reactive.tasks.task2.adapter.PercentageAdapter;
import cl.tenpo.learning.reactive.tasks.task2.domain.model.Percentage;
import cl.tenpo.learning.reactive.tasks.task2.domain.port.CachePort;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.cache.PercentageCache;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@AllArgsConstructor
public class PercentageRedisRepository implements CachePort<Percentage> {
    private final GenericRedisRepository<PercentageCache> repository;

    @Override
    public Mono<Percentage> find(final String key) {
        return repository.find(key)
            .map(PercentageAdapter::toModel);
    }

    @Override
    public Mono<Percentage> save(final String key, final Percentage percentage) {
        return Mono.just(percentage)
            .map(PercentageAdapter::toCache)
            .flatMap(p -> repository.save(key, p))
            .map(PercentageAdapter::toModel);
    }

    @Override
    public Mono<Boolean> delete(final String key) {
        return repository.delete(key);
    }

    @Override
    public Mono<Long> deleteAll() {
        return repository.deleteAll();
    }
}
