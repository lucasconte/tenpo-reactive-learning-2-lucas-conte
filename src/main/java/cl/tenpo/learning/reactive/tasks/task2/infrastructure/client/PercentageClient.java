package cl.tenpo.learning.reactive.tasks.task2.infrastructure.client;

import cl.tenpo.learning.reactive.tasks.task2.adapter.PercentageAdapter;
import cl.tenpo.learning.reactive.tasks.task2.domain.exception.rest.RestException;
import cl.tenpo.learning.reactive.tasks.task2.domain.exception.rest.RetryExhaustedException;
import cl.tenpo.learning.reactive.tasks.task2.domain.model.Percentage;
import cl.tenpo.learning.reactive.tasks.task2.domain.port.PercentagePort;
import cl.tenpo.learning.reactive.tasks.task2.domain.port.RetryExhaustedNotifier;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.rest.outbound.response.GetPercentageResponse;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.rest.outbound.response.ResponseData;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.properties.LearningReactiveProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
@Slf4j
public class PercentageClient implements PercentagePort {
    private final String className = this.getClass().getSimpleName();
    private final RestClient restClient;
    private final LearningReactiveProperties properties;
    private final RetryExhaustedNotifier retryExhaustedNotifier;

    @Override
    public Mono<Percentage> getPercentage() {
        return Mono.just(PercentageAdapter.getPercentageRequest(properties.getPercentage()))
                .doFirst(() -> log.info("[{}] Getting percentage", className))
                .flatMap(request -> restClient.execute(request, GetPercentageResponse.class))
                .map(ResponseData::getBody)
                .map(PercentageAdapter::toModel)
                .doOnError(RetryExhaustedException.class, error -> retryExhaustedNotifier.notify(error))
                .doOnError(RestException.class, error -> log.error(
                        "[{}] Error getting percentage -> error [code: {}, message: {}]",
                        className, error.getStatus().value(), error.getMessage(), error));
    }
}
