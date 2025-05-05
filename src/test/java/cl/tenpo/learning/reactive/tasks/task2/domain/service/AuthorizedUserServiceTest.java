package cl.tenpo.learning.reactive.tasks.task2.domain.service;

import cl.tenpo.learning.reactive.tasks.task2.domain.exception.BaseException;
import cl.tenpo.learning.reactive.tasks.task2.domain.model.AuthorizedUser;
import cl.tenpo.learning.reactive.tasks.task2.domain.port.AuthorizedUserPort;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.rest.common.ErrorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;

import static cl.tenpo.learning.reactive.tasks.task2.domain.utils.TestFileUtils.AUTHORIZED_USER_MODEL_TEST_FILE;
import static cl.tenpo.learning.reactive.tasks.task2.domain.utils.TestFileUtils.readFile;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorizedUserServiceTest {
    @Mock
    private AuthorizedUserPort authorizedUserPort;
    @InjectMocks
    private AuthorizedUserService target;

    @Nested
    @DisplayName("Create")
    class CreateTest {
        @DisplayName("Create should throw error when user already exists")
        @Test
        void createShouldThrowUserAlreadyExistsError() throws IOException {
            final AuthorizedUser authorizedUser = readFile(AUTHORIZED_USER_MODEL_TEST_FILE, AuthorizedUser.class);
            when(authorizedUserPort.existsByEmail(authorizedUser.email())).thenReturn(Mono.just(Boolean.TRUE));

            StepVerifier.create(target.create(authorizedUser))
                    .expectErrorSatisfies(error -> {
                        BaseException ex = (BaseException) error; // casteo
                        assertThat(ex.getType()).isEqualTo(ErrorType.authorized_user_already_exist.name());
                        assertThat(ex.getMessage()).isEqualTo("Authorized user fake.user@email.com already exist");
                    })
                    .verify();
            verify(authorizedUserPort, never()).create(any());
        }

        @DisplayName("Create should be OK")
        @Test
        void createShouldBeOK() throws IOException {
            final AuthorizedUser authorizedUser = readFile(AUTHORIZED_USER_MODEL_TEST_FILE, AuthorizedUser.class);
            when(authorizedUserPort.existsByEmail(authorizedUser.email())).thenReturn(Mono.just(Boolean.FALSE));
            when(authorizedUserPort.create(authorizedUser)).thenReturn(Mono.just(authorizedUser));

            StepVerifier.create(target.create(authorizedUser))
                    .assertNext(act -> assertThat(act).isEqualTo(authorizedUser))
                    .verifyComplete();
        }
    }

    @Nested
    @DisplayName("Delete")
    class DeleteTest {
        @DisplayName("Delete should throw error when user not found")
        @Test
        void deleteShouldThrowUserNotFoundError() throws IOException {
            final AuthorizedUser authorizedUser = readFile(AUTHORIZED_USER_MODEL_TEST_FILE, AuthorizedUser.class);
            when(authorizedUserPort.findByEmail(authorizedUser.email())).thenReturn(Mono.empty());

            StepVerifier.create(target.delete(authorizedUser))
                    .expectErrorSatisfies(error -> {
                        BaseException ex = (BaseException) error; // casteo
                        assertThat(ex.getType()).isEqualTo(ErrorType.authorized_user_not_found.name());
                        assertThat(ex.getMessage()).isEqualTo("Authorized user fake.user@email.com was not found");
                    })
                    .verify();
            verify(authorizedUserPort, never()).delete(any());
        }

        @DisplayName("Delete should be OK")
        @Test
        void deleteShouldBeOK() throws IOException {
            final AuthorizedUser authorizedUser = readFile(AUTHORIZED_USER_MODEL_TEST_FILE, AuthorizedUser.class);
            when(authorizedUserPort.findByEmail(authorizedUser.email())).thenReturn(Mono.just(authorizedUser));
            when(authorizedUserPort.delete(authorizedUser)).thenReturn(Mono.just(authorizedUser));

            StepVerifier.create(target.delete(authorizedUser))
                    .assertNext(act -> assertThat(act).isEqualTo(authorizedUser))
                    .verifyComplete();
        }
    }
}
