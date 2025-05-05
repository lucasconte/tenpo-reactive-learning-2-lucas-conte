package cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.rest.outbound.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@Accessors(chain = true, fluent = true)
public class RequestData<T> {
    private HttpMethod method;
    private String url;
    @Builder.Default
    private HttpHeaders headers = new HttpHeaders();
    private T body;
    @Builder.Default
    private Integer retries = 0;
    @Builder.Default
    private Integer retryDelay = 300;
    private Integer timeout;
}
