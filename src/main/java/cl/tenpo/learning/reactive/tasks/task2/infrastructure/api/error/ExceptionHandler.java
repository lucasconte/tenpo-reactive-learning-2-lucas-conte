package cl.tenpo.learning.reactive.tasks.task2.infrastructure.api.error;

import cl.tenpo.learning.reactive.tasks.task2.domain.exception.BaseException;
import cl.tenpo.learning.reactive.tasks.task2.domain.exception.rest.RestException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.function.BiFunction;

@Component
public class ExceptionHandler {
    private final CustomExceptionHandler customExceptionHandler;

    private final Map<
            Class<? extends Throwable>,
            BiFunction<? extends Throwable, ServerRequest, Mono<ServerResponse>>>
            exceptionHandlers;

    public ExceptionHandler(final CustomExceptionHandler customExceptionHandler) {
        this.customExceptionHandler = customExceptionHandler;
        this.exceptionHandlers =
                Map.ofEntries(
                        entry(BaseException.class, customExceptionHandler::handleBaseException),
                        entry(RestException.class, customExceptionHandler::handleRestException)
                );
    }

    @SuppressWarnings("unchecked")
    public <T extends Throwable> Mono<ServerResponse> handle(
            final T err, final ServerRequest request) {
        return ((BiFunction<T, ServerRequest, Mono<ServerResponse>>)
                exceptionHandlers.getOrDefault(err.getClass(), customExceptionHandler::handleUnknownException))
                .apply(err, request);
    }

    private <T extends Throwable>
    Map.Entry<Class<T>, BiFunction<T, ServerRequest, Mono<ServerResponse>>> entry(
            final Class<T> clazz, final BiFunction<T, ServerRequest, Mono<ServerResponse>> handler) {
        return Map.entry(clazz, (err, req) -> handler.apply(clazz.cast(err), req));
    }
}
