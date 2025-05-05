package cl.tenpo.learning.reactive.tasks.task2.domain.model;

import lombok.Builder;

import java.time.Instant;

@Builder
public record ApiCallHistory(String id, String method, String endpoint, String requestBody, String responseBody,
                             boolean isError, Instant createdAt) {
}
