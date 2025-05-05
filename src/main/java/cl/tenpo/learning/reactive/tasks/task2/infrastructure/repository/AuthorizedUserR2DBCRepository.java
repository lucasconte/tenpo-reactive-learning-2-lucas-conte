package cl.tenpo.learning.reactive.tasks.task2.infrastructure.repository;

import cl.tenpo.learning.reactive.tasks.task2.adapter.AuthorizedUserAdapter;
import cl.tenpo.learning.reactive.tasks.task2.domain.model.AuthorizedUser;
import cl.tenpo.learning.reactive.tasks.task2.domain.port.AuthorizedUserPort;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.repository.dao.AuthorizedUserDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@AllArgsConstructor
public class AuthorizedUserR2DBCRepository implements AuthorizedUserPort {
    private final AuthorizedUserDao dao;


    @Override
    public Mono<AuthorizedUser> create(final AuthorizedUser authorizedUser) {
        return Mono.just(authorizedUser)
                .map(AuthorizedUserAdapter::toEntity)
                .flatMap(dao::save)
                .map(AuthorizedUserAdapter::toModel);
    }

    @Override
    public Mono<AuthorizedUser> delete(final AuthorizedUser authorizedUser) {
        return Mono.just(authorizedUser)
                .map(AuthorizedUserAdapter::toEntity)
                .flatMap(dao::delete)
                .thenReturn(authorizedUser);
    }

    @Override
    public Mono<Boolean> existsByEmail(final String email) {
        return dao.existsByEmail(email);
    }

    @Override
    public Mono<AuthorizedUser> findByEmail(final String email) {
        return dao.findByEmail(email)
                .map(AuthorizedUserAdapter::toModel);
    }
}
