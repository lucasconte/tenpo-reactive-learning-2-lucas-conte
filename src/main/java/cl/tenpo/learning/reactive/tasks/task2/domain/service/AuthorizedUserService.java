package cl.tenpo.learning.reactive.tasks.task2.domain.service;

import cl.tenpo.learning.reactive.tasks.task2.domain.model.AuthorizedUser;
import cl.tenpo.learning.reactive.tasks.task2.domain.port.AuthorizedUserPort;
import cl.tenpo.learning.reactive.tasks.task2.utils.ErrorHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@AllArgsConstructor
public class AuthorizedUserService {
    private final String className = this.getClass().getSimpleName();
    private AuthorizedUserPort authorizedUserPort;

    public Mono<AuthorizedUser> create(final AuthorizedUser authorizedUser) {
        return Mono.just(authorizedUser)
                .doFirst(() -> log.info("[{}] Create authorized user [{}]", className, authorizedUser))
                .filterWhen(user -> notExistsByEmail(user.email()))
                .flatMap(authorizedUserPort::create)
                .switchIfEmpty(Mono.defer(() -> Mono.error(
                        ErrorHandler.authorizedUserAlreadyExist(authorizedUser.email()))));
    }

    private Mono<Boolean> notExistsByEmail(final String email) {
        return authorizedUserPort.existsByEmail(email)
                .map(Boolean.FALSE::equals);
    }

    public Mono<Boolean> existsByEmail(final String email) {
        return authorizedUserPort.existsByEmail(email)
                .map(Boolean.TRUE::equals);
    }

    public Mono<AuthorizedUser> delete(final AuthorizedUser authorizedUser) {
        return Mono.just(authorizedUser.email())
                .doFirst(() -> log.info("[{}] Delete authorized user [{}]", className, authorizedUser))
                .flatMap(authorizedUserPort::findByEmail)
                .flatMap(authorizedUserPort::delete)
                .switchIfEmpty(Mono.defer(() -> Mono.error(
                        ErrorHandler.authorizedUserNotFound(authorizedUser.email()))));
    }
}
