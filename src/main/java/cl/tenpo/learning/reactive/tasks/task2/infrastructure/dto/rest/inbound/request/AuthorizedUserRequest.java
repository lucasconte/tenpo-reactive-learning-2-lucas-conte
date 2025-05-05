package cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.rest.inbound.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record AuthorizedUserRequest(@NotBlank @Email String email) {
}
