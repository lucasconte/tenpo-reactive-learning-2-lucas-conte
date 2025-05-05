package cl.tenpo.learning.reactive.tasks.task2.domain.port;

import cl.tenpo.learning.reactive.tasks.task2.domain.model.Percentage;
import reactor.core.publisher.Mono;

public interface PercentagePort {
    Mono<Percentage> getPercentage();
}
