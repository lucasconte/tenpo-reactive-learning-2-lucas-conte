package cl.tenpo.learning.reactive.tasks.task2.domain.exception.rest;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class ServerException extends RestException {

    public ServerException(final String message, final HttpStatusCode status) {
        super(message, status);
    }

    public ServerException(final String message, final HttpStatusCode statusCode, final Throwable cause) {
        super(message, statusCode, cause);
    }
}
