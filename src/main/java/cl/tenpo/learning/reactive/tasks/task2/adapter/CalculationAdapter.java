package cl.tenpo.learning.reactive.tasks.task2.adapter;

import cl.tenpo.learning.reactive.tasks.task2.domain.model.Calculation;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.rest.inbound.request.CalculationCreateRequest;

public final class CalculationAdapter {
    private CalculationAdapter() {
    }

    public static Calculation toModel(final CalculationCreateRequest request) {
        return Calculation.builder()
                .number1(request.number1())
                .number2(request.number2())
                .build();
    }
}
