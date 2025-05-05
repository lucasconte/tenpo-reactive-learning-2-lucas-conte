package cl.tenpo.learning.reactive.tasks.task2.infrastructure.messaging.producer;

import cl.tenpo.learning.reactive.tasks.task2.adapter.RetryExhaustedAdapter;
import cl.tenpo.learning.reactive.tasks.task2.domain.exception.rest.RetryExhaustedException;
import cl.tenpo.learning.reactive.tasks.task2.domain.port.RetryExhaustedNotifier;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.event.RetryExhaustedEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static cl.tenpo.learning.reactive.tasks.task2.utils.Topics.CR_RETRY_EXHAUSTED;

@Component
@AllArgsConstructor
@Slf4j
public class RetryExhaustedProducer implements RetryExhaustedNotifier {
    private final String className = this.getClass().getSimpleName();
    private final ReactiveKafkaProducerTemplate<String, RetryExhaustedEvent> producerTemplate;

    @Override
    public Mono<Void> notify(final RetryExhaustedException retryExhausted) {
        Mono.just(retryExhausted)
                .map(RetryExhaustedAdapter::toEvent)
                .doOnNext(event -> log.info("[{}][{}] Sending event [{}]", className, CR_RETRY_EXHAUSTED, event))
                .flatMap(event -> producerTemplate.send(CR_RETRY_EXHAUSTED, event))
                .doOnError(error -> log.error("[{}][{}] Error sending event for [{}]", className, CR_RETRY_EXHAUSTED, retryExhausted, error))
                .subscribe();
        return Mono.empty();
    }
}
