package cl.tenpo.learning.reactive.tasks.task2.domain.exception.rest;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public abstract class RestException extends RuntimeException {
    private final HttpStatusCode status;

    public RestException(final String message, final HttpStatusCode status) {
        super(message);
        this.status = status;
    }

    public RestException(final String message, final HttpStatusCode status, final Throwable cause) {
        super(message, cause);
        this.status = status;
    }
}
