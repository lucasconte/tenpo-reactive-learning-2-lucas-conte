package cl.tenpo.learning.reactive.tasks.task2.adapter;

import cl.tenpo.learning.reactive.tasks.task2.domain.exception.rest.RetryExhaustedException;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.event.RetryExhaustedEvent;

public final class RetryExhaustedAdapter {
    private RetryExhaustedAdapter() {
    }

    private static final String EVENT_ERROR_TEMPLATE = "Total retries: [%s] - Method: [%s] - Url: [%s] - Error: [%s]";

    public static RetryExhaustedEvent toEvent(final RetryExhaustedException retryExhausted) {
        return RetryExhaustedEvent.builder()
                .error(String.format(
                        EVENT_ERROR_TEMPLATE,
                        retryExhausted.getTotalRetries(),
                        retryExhausted.getMethod(),
                        retryExhausted.getUrl(),
                        retryExhausted.getMessage()
                ))
                .build();
    }
}
