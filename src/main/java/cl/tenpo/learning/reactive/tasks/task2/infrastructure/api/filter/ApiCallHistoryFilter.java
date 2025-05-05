package cl.tenpo.learning.reactive.tasks.task2.infrastructure.api.filter;

import cl.tenpo.learning.reactive.tasks.task2.infrastructure.api.filter.decorator.RequestBodyCaptureDecorator;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.api.filter.decorator.ResponseBodyCaptureDecorator;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.api.handler.ApiCallHistoryHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class ApiCallHistoryFilter implements WebFilter {
    private final ApiCallHistoryHandler apiCallHistoryHandler;

    @Override
    public Mono<Void> filter(final ServerWebExchange exchange, final WebFilterChain chain) {
        StringBuilder requestBodyBuilder = new StringBuilder();
        StringBuilder responseBodyBuilder = new StringBuilder();

        ServerHttpRequestDecorator decoratedRequest = new RequestBodyCaptureDecorator(exchange.getRequest(), requestBodyBuilder);
        ServerHttpResponse originalResponse = exchange.getResponse();
        ServerHttpResponseDecorator decoratedResponse = new ResponseBodyCaptureDecorator(originalResponse, responseBodyBuilder);

        ServerWebExchange mutatedExchange = exchange.mutate()
                .request(decoratedRequest)
                .response(decoratedResponse)
                .build();

        return chain.filter(mutatedExchange)
                .doFinally(signalType -> {
                    apiCallHistoryHandler.apiCallHistoryCreateHandler(
                            exchange.getRequest(),
                            requestBodyBuilder.toString(),
                            responseBodyBuilder.toString(),
                            originalResponse.getStatusCode().isError()
                    ).subscribe();
                });
    }
}
