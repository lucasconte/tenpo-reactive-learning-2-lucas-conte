package cl.tenpo.learning.reactive.tasks.task2.domain.service;

import cl.tenpo.learning.reactive.tasks.task2.domain.model.Calculation;
import cl.tenpo.learning.reactive.tasks.task2.utils.NumberUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
@Slf4j
@AllArgsConstructor
public class CalculationService {
    private final String className = this.getClass().getSimpleName();
    private final PercentageService percentageService;

    public Mono<BigDecimal> calculate(final Calculation calculation) {
        return Mono.zip(
                Mono.just(calculation.number1().add(calculation.number2())),
                percentageService.getPercentage(),
                NumberUtils::addPercentage
        ).doFirst(() -> log.info("[{}] Calculate for [{}]", className, calculation));
    }
}
