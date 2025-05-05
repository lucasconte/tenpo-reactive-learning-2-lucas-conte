package cl.tenpo.learning.reactive.tasks.task2.infrastructure.configuration.messaging;

import cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.event.RetryExhaustedEvent;
import cl.tenpo.learning.reactive.tasks.task2.utils.Topics;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.SenderOptions;

import java.util.Collections;
import java.util.Map;

@Configuration
public class KafkaConfig {
    //producer

    @Bean
    public ReactiveKafkaProducerTemplate<String, RetryExhaustedEvent> retryExhaustedProducerTemplate(KafkaProperties kafkaProperties) {
        Map<String, Object> options = kafkaProperties.buildProducerProperties(null);
        options.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new ReactiveKafkaProducerTemplate<>(SenderOptions.create(options));
    }

    //consumer

    @Bean
    public ReactiveKafkaConsumerTemplate<String, RetryExhaustedEvent> retryExhaustedConsumerTemplate(KafkaProperties kafkaProperties) {
        Map<String, Object> options = kafkaProperties.buildConsumerProperties(null);
        options.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        options.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        options.put(JsonDeserializer.VALUE_DEFAULT_TYPE, RetryExhaustedEvent.class);
        ReceiverOptions<String, RetryExhaustedEvent> receiverOptions = ReceiverOptions.<String, RetryExhaustedEvent>create(options)
                .subscription(Collections.singletonList(Topics.CR_RETRY_EXHAUSTED));
        return new ReactiveKafkaConsumerTemplate<>(receiverOptions);
    }
}
