package cl.tenpo.learning.reactive.tasks.task2.infrastructure.api.filter.decorator;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import reactor.core.publisher.Flux;

import java.nio.charset.StandardCharsets;

public class RequestBodyCaptureDecorator extends ServerHttpRequestDecorator {

    private final StringBuilder capturedBody;

    public RequestBodyCaptureDecorator(final ServerHttpRequest delegate,
                                       final StringBuilder capturedBody) {
        super(delegate);
        this.capturedBody = capturedBody;
    }

    @Override
    public Flux<DataBuffer> getBody() {
        Flux<DataBuffer> originalBody = super.getBody();

        return originalBody.flatMap(dataBuffer -> {
            byte[] bytes = new byte[dataBuffer.readableByteCount()];
            dataBuffer.read(bytes);
            capturedBody.append(new String(bytes, StandardCharsets.UTF_8));
            DataBufferUtils.release(dataBuffer);
            DataBuffer newDataBuffer = dataBuffer.factory().wrap(bytes);
            return Flux.just(newDataBuffer);
        });
    }
}
