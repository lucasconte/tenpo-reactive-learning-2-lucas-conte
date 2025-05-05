package cl.tenpo.learning.reactive.tasks.task2.infrastructure.api.handler;

import cl.tenpo.learning.reactive.tasks.task2.domain.model.Percentage;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.client.PercentageClient;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.rest.inbound.request.CalculationCreateRequest;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.repository.cache.PercentageRedisRepository;
import cl.tenpo.learning.reactive.tasks.task2.utils.Paths;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.math.BigDecimal;

import static cl.tenpo.learning.reactive.tasks.task2.domain.utils.TestFileUtils.CALCULATION_CREATE_REQUEST_DTO_TEST_FILE;
import static cl.tenpo.learning.reactive.tasks.task2.domain.utils.TestFileUtils.PERCENTAGE_MODEL_TEST_FILE;
import static cl.tenpo.learning.reactive.tasks.task2.domain.utils.TestFileUtils.readFile;
import static cl.tenpo.learning.reactive.tasks.task2.infrastructure.configuration.cache.CacheConfig.PERCENTAGE_CACHE_ID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class CalculationHandlerIT {

    @Autowired
    private WebTestClient webClient;

    @Autowired
    private PercentageRedisRepository percentageRedisRepository;

    @MockBean
    private PercentageClient percentageClient;

    @Test
    void testCalculationSuccess() throws IOException {
        final CalculationCreateRequest request = readFile(CALCULATION_CREATE_REQUEST_DTO_TEST_FILE, CalculationCreateRequest.class);
        final Percentage percentage = readFile(PERCENTAGE_MODEL_TEST_FILE, Percentage.class);

        when(percentageClient.getPercentage())
                .thenReturn(Mono.just(percentage));

        webClient.post()
                .uri("/" + Paths.CALCULATION_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BigDecimal.class)
                .value(result -> {
                    assertThat(result).isEqualTo(new BigDecimal("11.00"));
                });

        StepVerifier.create(percentageRedisRepository.find(PERCENTAGE_CACHE_ID.toString()))
                .assertNext(act -> Assertions.assertThat(act).isEqualTo(percentage))
                .verifyComplete();
    }
}
