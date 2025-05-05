package cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.cache;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record PercentageCache(
    UUID id,
    BigDecimal value
) {
}

