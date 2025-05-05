package cl.tenpo.learning.reactive.tasks.task2.infrastructure.api.error;

import cl.tenpo.learning.reactive.tasks.task2.domain.exception.BaseException;
import cl.tenpo.learning.reactive.tasks.task2.domain.exception.rest.RestException;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.rest.common.ErrorResponse;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.rest.common.ErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class CustomExceptionHandler {

    public Mono<ServerResponse> handleUnknownException(
            final Throwable err, final ServerRequest request) {
        final String message = Optional.ofNullable(err.getMessage()).orElse(ErrorType.unknown.getMessage());
        return request
                .bodyToFlux(Map.class)
                .collectList()
                .flatMap(body -> buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                        ErrorType.unknown.name(),
                        Collections.singletonList(message)));
    }

    public Mono<ServerResponse> handleBaseException(
            final BaseException exception, final ServerRequest serverRequest) {
        return buildErrorResponse(exception.getStatus(), exception.getType(),
                Collections.singletonList(exception.getMessage()));
    }

    public Mono<ServerResponse> handleRestException(
            final RestException exception, final ServerRequest serverRequest) {
        return buildErrorResponse(HttpStatus.valueOf(exception.getStatus().value()), ErrorType.external_api_error.name(),
                Collections.singletonList(exception.getMessage()));
    }

    private static Mono<ServerResponse> buildErrorResponse(
            final HttpStatus httpStatus, final String code, final List<String> errors) {
        return ServerResponse.status(httpStatus)
                .body(BodyInserters.fromValue(new ErrorResponse(code, errors)));
    }
}
