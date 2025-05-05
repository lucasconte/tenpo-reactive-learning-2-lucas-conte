package cl.tenpo.learning.reactive.tasks.task2.infrastructure.api.handler;

import cl.tenpo.learning.reactive.tasks.task2.adapter.ApiCallHistoryAdapter;
import cl.tenpo.learning.reactive.tasks.task2.domain.service.ApiCallHistoryService;
import cl.tenpo.learning.reactive.tasks.task2.utils.Headers;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.Instant;


@Component
@AllArgsConstructor
@Slf4j
public class ApiCallHistoryHandler {
    private final ApiCallHistoryService apiCallHistoryService;

    public Mono<Void> apiCallHistoryCreateHandler(final ServerHttpRequest request,
                                                  final String requestBody,
                                                  final String responseBody,
                                                  final boolean isError) {
        return Mono.just(request)
                .map(rq -> ApiCallHistoryAdapter.toModel(rq, requestBody, responseBody, isError, Instant.now()))
                .flatMap(apiCallHistoryService::create)
                .then();
    }

    public Mono<ServerResponse> apiCallHistoryGetHandler(final ServerRequest request) {
        return apiCallHistoryService.findAll(request.headers().firstHeader(Headers.AUTHORIZED_USER))
                .collectList()
                .flatMap(result -> ServerResponse.ok().bodyValue(result));
    }
}
