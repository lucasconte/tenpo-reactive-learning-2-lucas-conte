package cl.tenpo.learning.reactive.tasks.task2.infrastructure.messaging.consumer;

import cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.event.RetryExhaustedEvent;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Slf4j
@Component
@AllArgsConstructor
public class RetryExhaustedConsumer {
    private final String className = getClass().getSimpleName();
    private final ReactiveKafkaConsumerTemplate<String, RetryExhaustedEvent> consumerTemplate;

    @PostConstruct
    public void run() {
        retryExhaustedStream().subscribe();
    }

    private Flux<RetryExhaustedEvent> retryExhaustedStream() {
        return consumerTemplate
            .receiveAutoAck()
            .map(ConsumerRecord::value)
            .doOnNext(evt -> log.info("[{}] Processing retry exhausted event -> {}", className, evt))
            .onErrorContinue(
                (err, evt) ->
                    log.error("[{}] Error occurred while processing event -> {}", className, evt, err));
    }
}
