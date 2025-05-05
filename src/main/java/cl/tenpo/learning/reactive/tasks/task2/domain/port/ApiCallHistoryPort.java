package cl.tenpo.learning.reactive.tasks.task2.domain.port;

import cl.tenpo.learning.reactive.tasks.task2.domain.model.ApiCallHistory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ApiCallHistoryPort {
    Mono<ApiCallHistory> create(ApiCallHistory apiCallHistory);
    Flux<ApiCallHistory> findAll();
}
