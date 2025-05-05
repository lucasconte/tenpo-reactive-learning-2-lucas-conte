package cl.tenpo.learning.reactive.tasks.task2.infrastructure.entity;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Builder
@Document(collection = "api_call_history")
public record ApiCallHistoryEntity(@Id String id, String method, String endpoint, String requestBody, String responseBody,
                                   boolean isError, Instant createdAt) {
}
