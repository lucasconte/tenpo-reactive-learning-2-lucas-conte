package cl.tenpo.learning.reactive.tasks.task2.domain.model;

import lombok.Builder;

import java.time.Instant;

@Builder
public record AuthorizedUser(Long id, String email, Instant createdAt) {
}
