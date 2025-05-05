package cl.tenpo.learning.reactive.tasks.task2.domain.model;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder(toBuilder = true)
public record Percentage(
    UUID id,
    BigDecimal value
) {
}

