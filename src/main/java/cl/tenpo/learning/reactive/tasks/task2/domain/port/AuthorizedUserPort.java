package cl.tenpo.learning.reactive.tasks.task2.domain.port;

import cl.tenpo.learning.reactive.tasks.task2.domain.model.AuthorizedUser;
import reactor.core.publisher.Mono;

public interface AuthorizedUserPort {
    Mono<AuthorizedUser> create(AuthorizedUser authorizedUser);
    Mono<AuthorizedUser> delete(AuthorizedUser authorizedUser);
    Mono<Boolean> existsByEmail(String email);
    Mono<AuthorizedUser> findByEmail(String email);
}
