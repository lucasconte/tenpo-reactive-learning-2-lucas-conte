package cl.tenpo.learning.reactive.tasks.task2.adapter;

import cl.tenpo.learning.reactive.tasks.task2.domain.model.ApiCallHistory;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.entity.ApiCallHistoryEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.time.Instant;

public final class ApiCallHistoryAdapter {
    private ApiCallHistoryAdapter() {
    }

    public static ApiCallHistory toModel(final ServerHttpRequest request,
                                         final String requestBody,
                                         final String responseBody,
                                         final boolean isError,
                                         final Instant createdAt) {
        return ApiCallHistory.builder()
                .endpoint(request.getURI().getPath())
                .method(request.getMethod().name())
                .requestBody(requestBody)
                .responseBody(responseBody)
                .isError(isError)
                .createdAt(createdAt)
                .build();
    }

    public static ApiCallHistory toModel(final ApiCallHistoryEntity entity) {
        return ApiCallHistory.builder()
                .id(entity.id())
                .method(entity.method())
                .endpoint(entity.endpoint())
                .requestBody(entity.requestBody())
                .responseBody(entity.responseBody())
                .createdAt(entity.createdAt())
                .isError(entity.isError())
                .build();
    }

    public static ApiCallHistoryEntity toEntity(final ApiCallHistory model) {
        return ApiCallHistoryEntity.builder()
                .id(model.id())
                .method(model.method())
                .endpoint(model.endpoint())
                .requestBody(model.requestBody())
                .responseBody(model.responseBody())
                .createdAt(model.createdAt())
                .isError(model.isError())
                .build();
    }
}
