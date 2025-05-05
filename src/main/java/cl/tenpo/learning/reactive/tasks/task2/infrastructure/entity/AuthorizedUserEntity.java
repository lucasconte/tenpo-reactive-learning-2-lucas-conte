package cl.tenpo.learning.reactive.tasks.task2.infrastructure.entity;

import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Builder
@Table(name = "authorized_users")
public record AuthorizedUserEntity(
    @Id Long id,
    String email,
    @CreatedDate Instant createdAt) {}
