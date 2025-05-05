package cl.tenpo.learning.reactive.tasks.task2.domain.model;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record Calculation(BigDecimal number1, BigDecimal number2) {
}
