package cl.tenpo.learning.reactive.tasks.task2.infrastructure.api.handler;

import cl.tenpo.learning.reactive.tasks.task2.adapter.AuthorizedUserAdapter;
import cl.tenpo.learning.reactive.tasks.task2.domain.service.AuthorizedUserService;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.api.handler.extractor.RequestBodyExtractor;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.rest.inbound.request.AuthorizedUserRequest;
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
public class AuthorizedUserHandler {
    private final RequestBodyExtractor bodyExtractor;
    private final AuthorizedUserService authorizedUserService;

    public Mono<ServerResponse> createAuthorizedUserHandler(final ServerRequest request) {
       return bodyExtractor.extractAndValidate(request, AuthorizedUserRequest.class)
               .map(AuthorizedUserAdapter::toModel)
               .flatMap(authorizedUserService::create)
               .flatMap(result -> ServerResponse.ok().body(BodyInserters.fromValue(result)));
    }

    public Mono<ServerResponse> deleteAuthorizedUserHandler(final ServerRequest request) {
        return bodyExtractor.extractAndValidate(request, AuthorizedUserRequest.class)
                .map(AuthorizedUserAdapter::toModel)
                .flatMap(authorizedUserService::delete)
                .flatMap(result -> ServerResponse.ok().body(BodyInserters.fromValue(result)));
    }
}
