package cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.rest.inbound.response;

import java.math.BigDecimal;

public record CalculationCreateResponse(BigDecimal percentage, BigDecimal value) {
}
