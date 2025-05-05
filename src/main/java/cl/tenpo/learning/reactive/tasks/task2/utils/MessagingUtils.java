package cl.tenpo.learning.reactive.tasks.task2.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import reactor.core.publisher.Mono;

@Slf4j
public final class MessagingUtils {
    private MessagingUtils() {
    }

    public static <T> Mono<T> sendEvent(final String topic,
                                        final T event,
                                        final ReactiveKafkaProducerTemplate<String, T> producerTemplate) {
         Mono.just(event)
                .flatMap(evt -> producerTemplate.send(topic, evt))
                .doOnSuccess(sr -> log.info("[{}] Success sending event [{}]", topic, event))
                .doOnError(err -> log.error("[{}] Error sending event [{}]", event, topic, err))
                .onErrorResume(err -> Mono.empty())
                .subscribe();
        return Mono.just(event);
    }
}
