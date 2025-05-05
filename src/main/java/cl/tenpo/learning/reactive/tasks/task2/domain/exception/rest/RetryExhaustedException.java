package cl.tenpo.learning.reactive.tasks.task2.domain.exception.rest;

import cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.rest.outbound.request.RequestData;
import lombok.Getter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

@Getter
public class RetryExhaustedException extends RestException {
    private final HttpMethod method;
    private final String url;
    private final long totalRetries;

    public RetryExhaustedException(final RequestData requestData, final Throwable e) {
        super(e.getMessage(), HttpStatus.GATEWAY_TIMEOUT, e);
        this.method = requestData.method();
        this.url = requestData.url();
        this.totalRetries = requestData.retries();
    }

    public RetryExhaustedException(final HttpMethod method, final String url, final long retries) {
        super("", HttpStatus.GATEWAY_TIMEOUT);
        this.method = method;
        this.url = url;
        this.totalRetries = retries;
    }
}
