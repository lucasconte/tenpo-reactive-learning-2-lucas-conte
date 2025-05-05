package cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.event;

import lombok.Builder;

@Builder
public record RetryExhaustedEvent(String error) {
}
