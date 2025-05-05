package cl.tenpo.learning.reactive.tasks.task2.infrastructure.configuration.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
@AllArgsConstructor
@Profile("!test")
public class WebClientConfiguration {
    private final ObjectMapper mapper;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .clientConnector(buildClientConnector())
                .exchangeStrategies(buildStrategy())
                .build();
    }

    private ClientHttpConnector buildClientConnector() {
        final HttpClient httpClient = HttpClient.create();
        return new ReactorClientHttpConnector(httpClient.wiretap(true));
    }

    private ExchangeStrategies buildStrategy() {
        return ExchangeStrategies.builder().codecs(clientDefaultCodecsConfigurer -> {
            clientDefaultCodecsConfigurer.defaultCodecs()
                    .jackson2JsonDecoder(new Jackson2JsonDecoder(mapper, MediaType.APPLICATION_JSON));
            clientDefaultCodecsConfigurer.defaultCodecs()
                    .jackson2JsonEncoder(new Jackson2JsonEncoder(mapper, MediaType.APPLICATION_JSON));
        }).build();
    }
}
