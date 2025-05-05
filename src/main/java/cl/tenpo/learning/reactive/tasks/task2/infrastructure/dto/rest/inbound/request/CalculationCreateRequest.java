package cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.rest.inbound.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CalculationCreateRequest(@JsonProperty("number_1") @NotNull @DecimalMin(value = "1.0") BigDecimal number1,
                                       @JsonProperty("number_2") @NotNull @DecimalMin(value = "1.0") BigDecimal number2) {
}
