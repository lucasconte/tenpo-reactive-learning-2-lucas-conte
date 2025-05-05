package cl.tenpo.learning.reactive.tasks.task2.infrastructure.api.filter.decorator;

import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

public class ResponseBodyCaptureDecorator extends ServerHttpResponseDecorator {

    private final StringBuilder capturedBody;
    private final DefaultDataBufferFactory bufferFactory = new DefaultDataBufferFactory();

    public ResponseBodyCaptureDecorator(final ServerHttpResponse delegate,
                                        final StringBuilder capturedBody) {
        super(delegate);
        this.capturedBody = capturedBody;
    }

    @Override
    public Mono<Void> writeWith(final Publisher<? extends DataBuffer> body) {
        Flux<? extends DataBuffer> bodyFlux = Flux.from(body);
        return super.writeWith(
                bodyFlux.map(dataBuffer -> {
                    byte[] content = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(content);
                    capturedBody.append(new String(content, StandardCharsets.UTF_8));
                    return bufferFactory.wrap(content);
                })
        );
    }

    @Override
    public Mono<Void> writeAndFlushWith(Publisher<? extends Publisher<? extends DataBuffer>> body) {
        return writeWith(Flux.from(body).flatMapSequential(p -> p));
    }
}
