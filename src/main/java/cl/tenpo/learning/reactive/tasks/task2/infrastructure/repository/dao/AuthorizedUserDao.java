package cl.tenpo.learning.reactive.tasks.task2.infrastructure.repository.dao;

import cl.tenpo.learning.reactive.tasks.task2.infrastructure.entity.AuthorizedUserEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface AuthorizedUserDao extends R2dbcRepository<AuthorizedUserEntity, Long> {
    Mono<Boolean> existsByEmail(String email);
    Mono<AuthorizedUserEntity> findByEmail(String email);
}
