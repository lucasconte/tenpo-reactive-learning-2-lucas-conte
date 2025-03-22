package cl.tenpo.learning.reactive.tasks.task1;

import cl.tenpo.learning.reactive.utils.exception.AuthorizationTimeoutException;
import cl.tenpo.learning.reactive.utils.exception.PaymentProcessingException;
import cl.tenpo.learning.reactive.utils.service.TransactionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.concurrent.atomic.AtomicInteger;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class T1Question12Test {

    @InjectMocks
    private T1Question12 t1Question12;

    @Mock
    private TransactionService transactionServiceMock;

    @Test
    @DisplayName("PREGUNTA 12 - Transacción autorizada")
    void question12_uc1_test() {
        when(transactionServiceMock.authorizeTransaction(11111))
                .thenReturn(Mono.just("Autorizado"));

        StepVerifier.create(t1Question12.question12())
                .expectNext("Autorizado")
                .verifyComplete();

        verify(transactionServiceMock, times(1)).authorizeTransaction(11111);
        verifyNoMoreInteractions(transactionServiceMock);
    }

    @Test
    @DisplayName("PREGUNTA 12 - Transacción autorizada - 1 retry")
    void question12_uc2_test() {
        AtomicInteger counter = new AtomicInteger(0);
        when(transactionServiceMock.authorizeTransaction(11111))
                .thenReturn(Mono.defer(() -> {
                    if (counter.getAndIncrement() == 0) {
                        return Mono.error(new RuntimeException("Fallo inicial"));
                    } else {
                        return Mono.just("Autorizado");
                    }
                }));

        StepVerifier.create(t1Question12.question12())
                .expectNext("Autorizado")
                .verifyComplete();

        verify(transactionServiceMock, times(1)).authorizeTransaction(11111);
        verifyNoMoreInteractions(transactionServiceMock);
    }

    @Test
    @DisplayName("PREGUNTA 12 - Transacción autorizada - service timeout & AuthorizationTimeoutException")
    void question12_uc3_test() {
        when(transactionServiceMock.authorizeTransaction(11111)).thenReturn(Mono.never());

        StepVerifier.create(t1Question12.question12())
                .expectError(AuthorizationTimeoutException.class)
                .verify();

        verify(transactionServiceMock, times(1)).authorizeTransaction(11111);
        verifyNoMoreInteractions(transactionServiceMock);
    }

    @Test
    @DisplayName("PREGUNTA 12 - Transacción autorizada - 3 retries & PaymentProcessingException")
    void question12_uc4_test() {
        when(transactionServiceMock.authorizeTransaction(11111)).thenReturn(Mono.error(new RuntimeException("Falla crítica")));

        StepVerifier.create(t1Question12.question12())
                .expectError(PaymentProcessingException.class)
                .verify();

        verify(transactionServiceMock, times(1)).authorizeTransaction(11111);
        verifyNoMoreInteractions(transactionServiceMock);
    }
}
