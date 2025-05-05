package cl.tenpo.learning.reactive.tasks.task2.domain.service;

import cl.tenpo.learning.reactive.tasks.task2.domain.exception.rest.RestException;
import cl.tenpo.learning.reactive.tasks.task2.domain.model.Percentage;
import cl.tenpo.learning.reactive.tasks.task2.domain.port.CachePort;
import cl.tenpo.learning.reactive.tasks.task2.domain.port.PercentagePort;
import cl.tenpo.learning.reactive.tasks.task2.utils.ErrorHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static cl.tenpo.learning.reactive.tasks.task2.infrastructure.configuration.cache.CacheConfig.PERCENTAGE_CACHE_ID;
import static cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.rest.common.ErrorType.get_percentage_external;

@Service
@AllArgsConstructor
@Slf4j
public class PercentageService {
    private final String className = this.getClass().getSimpleName();
    private final PercentagePort percentagePort;
    private final CachePort<Percentage> cachePort;

    public Mono<BigDecimal> getPercentage() {
        return percentagePort.getPercentage()
                .flatMap(this::saveInCache)
                .onErrorResume(RestException.class, (error) -> getPercentageFallback())
                .map(Percentage::value)
                .doOnError(error -> log.error("[{}] Error getting percentage", className, error))
                .doOnSuccess(percentage -> log.info("[{}] Success on get percentage [{}]", className, percentage));
    }

    private Mono<Percentage> saveInCache(final Percentage percentage) {
        return Mono.just(percentage)
                .map(p -> p.toBuilder().id(PERCENTAGE_CACHE_ID).build())
                .flatMap(p -> cachePort.save(p.id().toString(), p));
    }

    private Mono<Percentage> getPercentageFallback() {
        return cachePort.find(PERCENTAGE_CACHE_ID.toString())
                .doFirst(() -> log.info("[{}] Getting Percentage fallback", className))
                .switchIfEmpty((Mono.error(() -> ErrorHandler.serverException(get_percentage_external))));
    }
}
