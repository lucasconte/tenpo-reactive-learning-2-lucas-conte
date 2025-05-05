package cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.rest.outbound.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;

@Getter
@Setter
@ToString
@Builder(toBuilder = true)
public class ResponseData<T> {
    private HttpHeaders headers;
    private HttpStatusCode status;
    private T body;
}
