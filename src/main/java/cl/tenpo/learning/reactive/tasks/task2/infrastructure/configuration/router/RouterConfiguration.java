package cl.tenpo.learning.reactive.tasks.task2.infrastructure.configuration.router;

import cl.tenpo.learning.reactive.tasks.task2.infrastructure.api.error.ExceptionHandler;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.api.handler.ApiCallHistoryHandler;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.api.handler.CalculationHandler;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.api.handler.AuthorizedUserHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static cl.tenpo.learning.reactive.tasks.task2.utils.Paths.API_CALL_HISTORY_PATH;
import static cl.tenpo.learning.reactive.tasks.task2.utils.Paths.AUTHORIZED_USER_PATH;
import static cl.tenpo.learning.reactive.tasks.task2.utils.Paths.CALCULATION_PATH;

@Configuration
@RequiredArgsConstructor
public class RouterConfiguration {
    private final ExceptionHandler exceptionHandler;
    private final CalculationHandler calculationHandler;
    private final AuthorizedUserHandler authorizedUserHandler;
    private final ApiCallHistoryHandler apiCallHistoryHandler;

    @Bean
    public RouterFunction<ServerResponse> mainRouter() {
        return RouterFunctions.route()
                .path(CALCULATION_PATH, this::calculationRouter)
                .path(AUTHORIZED_USER_PATH, this::authorizedUserRouter)
                .path(API_CALL_HISTORY_PATH, this::apiCallHistoryRouter)
                .onError(Throwable.class, exceptionHandler::handle)
                .build();
    }

    private RouterFunction<ServerResponse> calculationRouter() {
        return RouterFunctions.route()
                .POST(calculationHandler::createCalculationHandler)
                .build();
    }

    private RouterFunction<ServerResponse> authorizedUserRouter() {
        return RouterFunctions.route()
                .POST(authorizedUserHandler::createAuthorizedUserHandler)
                .DELETE(authorizedUserHandler::deleteAuthorizedUserHandler)
                .build();
    }

    private RouterFunction<ServerResponse> apiCallHistoryRouter() {
        return RouterFunctions.route()
                .GET(apiCallHistoryHandler::apiCallHistoryGetHandler)
                .build();
    }
}
