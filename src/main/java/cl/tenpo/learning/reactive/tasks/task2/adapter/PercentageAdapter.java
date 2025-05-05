package cl.tenpo.learning.reactive.tasks.task2.adapter;

import cl.tenpo.learning.reactive.tasks.task2.domain.model.Percentage;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.cache.PercentageCache;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.rest.outbound.request.RequestData;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.properties.EndpointProperties;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.rest.outbound.response.GetPercentageResponse;

public final class PercentageAdapter {
    private PercentageAdapter() {
    }

    public static RequestData<Void> getPercentageRequest(final EndpointProperties endpoint) {
        return RequestData.<Void>builder()
                .url(endpoint.url())
                .retries(endpoint.retries())
                .retryDelay(endpoint.retryDelay())
                .method(endpoint.getMethod())
                .build();
    }

    public static Percentage toModel(final PercentageCache cache) {
        return Percentage.builder()
                .id(cache.id())
                .value(cache.value())
                .build();
    }

    public static Percentage toModel(final GetPercentageResponse response) {
        return Percentage.builder()
                .value(response.percentage())
                .build();
    }

    public static PercentageCache toCache(final Percentage model) {
        return PercentageCache.builder()
                .id(model.id())
                .value(model.value())
                .build();
    }
}
