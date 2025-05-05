package cl.tenpo.learning.reactive.tasks.task2.infrastructure.client;

import cl.tenpo.learning.reactive.tasks.task2.domain.exception.rest.ClientException;
import cl.tenpo.learning.reactive.tasks.task2.domain.exception.rest.RestException;
import cl.tenpo.learning.reactive.tasks.task2.domain.exception.rest.RetryExhaustedException;
import cl.tenpo.learning.reactive.tasks.task2.domain.exception.rest.ServerException;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.rest.outbound.request.RequestData;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.rest.outbound.response.ResponseData;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;

import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;

@Slf4j
@Component
@AllArgsConstructor
public class RestClient {
    private static final String className = RestClient.class.getSimpleName();
    private static final Integer DEFAULT_TIMEOUT_MS = 2000;
    @Qualifier("webClient")
    private final WebClient webClient;

    private static final Map<HttpStatus.Series, Function<WebClientResponseException, RestException>> ERROR_MAPPERS =
            Map.of(
            HttpStatus.Series.CLIENT_ERROR, ex -> new ClientException(ex.getMessage(), ex.getStatusCode(), ex),
            HttpStatus.Series.SERVER_ERROR, ex -> new ServerException(ex.getMessage(), ex.getStatusCode(), ex)
            );

    public <T, R> Mono<ResponseData<T>> execute(final RequestData<R> requestData,
                                                final Class<T> responseClass) {
        log.info("[{}] [{}] {}", className, requestData.method(), requestData.url());
        return webClient
                .method(requestData.method())
                .uri(requestData.url())
                .headers(h -> h.addAll(requestData.headers()))
                .body(Objects.nonNull(requestData.body())
                        ? BodyInserters.fromValue(requestData.body())
                        : BodyInserters.empty())
                .retrieve()
                .toEntity(responseClass)
                .timeout(mapTimeout(requestData))
                .map(this::mapResponse)
                .onErrorMap(TimeoutException.class, this::mapTimeoutError)
                .onErrorMap(WebClientResponseException.class, this::mapResponseError)
                .retryWhen(handleRetry(requestData));
    }

    private Duration mapTimeout(final RequestData requestData) {
        return Optional.ofNullable(requestData.timeout())
                .map(Duration::ofMillis)
                .orElse(Duration.ofMillis(DEFAULT_TIMEOUT_MS));
    }

    private <T> ResponseData<T> mapResponse(final ResponseEntity<T> response) {
        return ResponseData.<T>builder()
                .headers(response.getHeaders())
                .status(response.getStatusCode())
                .body(response.getBody())
                .build();
    }

    private RestException mapResponseError(final WebClientResponseException ex) {
        return Optional.ofNullable(HttpStatus.resolve(ex.getStatusCode().value()))
                .map(HttpStatus::series)
                .map(series -> ERROR_MAPPERS
                        .getOrDefault(series,
                                e -> new ServerException(ex.getMessage(), ex.getStatusCode(), ex))
                        .apply(ex))
                .orElseGet(() -> new ServerException(ex.getMessage(), ex.getStatusCode(), ex));
    }

    private RestException mapTimeoutError(final TimeoutException ex) {
        return new ServerException(ex.getMessage(), HttpStatus.GATEWAY_TIMEOUT, ex);
    }

    private static <R> RetryBackoffSpec handleRetry(final RequestData<R> requestData) {
        return Retry.fixedDelay(requestData.retries(), Duration.ofMillis(requestData.retryDelay()))
                .filter(ServerException.class::isInstance)
                .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> {
                    final RetryExhaustedException ex = new RetryExhaustedException(requestData, retrySignal.failure());
                    log.error("[{}][{}] {} failed after total retries {} with error: {}",
                            className, requestData.method(), requestData.url(), requestData.retries(),
                            ex.getMessage());
                    return ex;
                });
    }
}
