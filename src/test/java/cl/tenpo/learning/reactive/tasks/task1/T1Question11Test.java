package cl.tenpo.learning.reactive.tasks.task1;

import cl.tenpo.learning.reactive.utils.model.UserAccount;
import cl.tenpo.learning.reactive.utils.service.AccountService;
import cl.tenpo.learning.reactive.utils.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class T1Question11Test {

    @InjectMocks
    private T1Question11 t1Question11;

    @Mock
    private UserService userServiceMock;

    @Mock
    private AccountService accountServiceMock;

    @Test
    void question11_uc1_test() {
        String userId = "123";

        Mono<UserAccount> result = t1Question11.question11(userId);

        StepVerifier.create(result)
                .expectSubscription()
                .assertNext(accountStatus -> {
                    assert accountStatus.user().id().equals(userId);
                    assert accountStatus.account().userId().equals(userId);
                })
                .verifyComplete();

        verify(userServiceMock, times(1)).getUserById("123");
        verifyNoMoreInteractions(userServiceMock);
        verify(accountServiceMock, times(1)).getAccountByUserId("123");
        verifyNoMoreInteractions(accountServiceMock);
    }

}
