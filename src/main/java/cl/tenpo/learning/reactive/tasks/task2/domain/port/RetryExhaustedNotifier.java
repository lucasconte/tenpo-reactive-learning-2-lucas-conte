package cl.tenpo.learning.reactive.tasks.task2.domain.port;

import cl.tenpo.learning.reactive.tasks.task2.domain.exception.rest.RetryExhaustedException;
import reactor.core.publisher.Mono;

public interface RetryExhaustedNotifier {
    Mono<Void> notify(RetryExhaustedException retryExhausted);
}
