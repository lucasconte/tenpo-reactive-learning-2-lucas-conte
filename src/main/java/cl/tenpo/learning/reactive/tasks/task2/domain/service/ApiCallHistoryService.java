package cl.tenpo.learning.reactive.tasks.task2.domain.service;

import cl.tenpo.learning.reactive.tasks.task2.domain.model.ApiCallHistory;
import cl.tenpo.learning.reactive.tasks.task2.domain.port.ApiCallHistoryPort;
import cl.tenpo.learning.reactive.tasks.task2.utils.ErrorHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@AllArgsConstructor
public class ApiCallHistoryService {
    private final String className = this.getClass().getSimpleName();
    private final ApiCallHistoryPort port;
    private final AuthorizedUserService authorizedUserService;

    public Mono<ApiCallHistory> create(final ApiCallHistory apiCallHistory) {
        return Mono.just(apiCallHistory)
                .doFirst(() -> log.info("[{}] Create api call history for [{}]", className, apiCallHistory))
                .flatMap(port::create)
                .onErrorResume(error -> {
                    log.error("[{}] Error creating api call history for [{}]", className ,apiCallHistory, error);
                    return Mono.empty();
                });
    }

    public Flux<ApiCallHistory> findAll(final String authorizedUserEmail) {
        return Mono.justOrEmpty(authorizedUserEmail)
                .doFirst(() -> log.info("[{}] Find all by user [{}]", className, authorizedUserEmail))
                .filterWhen(email -> authorizedUserService.existsByEmail(email))
                .switchIfEmpty(Mono.defer(() -> Mono.error(ErrorHandler.unauthorizedUser(authorizedUserEmail))))
                .flatMapMany(email -> port.findAll())
                .doOnError(error -> log.error("[{}] Error on find all by user [{}]", className, authorizedUserEmail, error));
    }

}
