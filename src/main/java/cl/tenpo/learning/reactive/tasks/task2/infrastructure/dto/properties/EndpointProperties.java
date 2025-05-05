package cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.properties;

import org.springframework.http.HttpMethod;

public record EndpointProperties(String method, String url, int retries, int retryDelay) {
    public HttpMethod getMethod() {
        return HttpMethod.valueOf(method.toUpperCase());
    }
}
