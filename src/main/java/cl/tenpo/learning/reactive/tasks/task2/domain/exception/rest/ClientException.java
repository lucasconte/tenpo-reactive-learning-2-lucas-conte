package cl.tenpo.learning.reactive.tasks.task2.domain.exception.rest;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class ClientException extends RestException {

    public ClientException(final String message, final HttpStatusCode status) {
        super(message, status);
    }

    public ClientException(final String message, final HttpStatusCode statusCode, final Throwable cause) {
        super(message, statusCode, cause);
    }
}
