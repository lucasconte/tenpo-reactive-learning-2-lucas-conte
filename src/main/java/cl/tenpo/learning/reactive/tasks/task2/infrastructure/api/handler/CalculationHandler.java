package cl.tenpo.learning.reactive.tasks.task2.infrastructure.api.handler;

import cl.tenpo.learning.reactive.tasks.task2.adapter.CalculationAdapter;
import cl.tenpo.learning.reactive.tasks.task2.domain.service.CalculationService;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.api.handler.extractor.RequestBodyExtractor;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.rest.inbound.request.CalculationCreateRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
@Slf4j
public class CalculationHandler {
    private final RequestBodyExtractor bodyExtractor;
    private final CalculationService calculationService;

    public Mono<ServerResponse> createCalculationHandler(final ServerRequest request) {
        return bodyExtractor.extractAndValidate(request, CalculationCreateRequest.class)
                .map(CalculationAdapter::toModel)
                .flatMap(calculationService::calculate)
                .flatMap(result -> ServerResponse.ok().body(BodyInserters.fromValue(result)));
    }
}
