package cl.tenpo.learning.reactive.modules.module2.sec04_errors;

import reactor.core.publisher.Mono;

import java.io.IOException;

public class Lec05CombiningErrorHandlers {

    public static void main(String[] args) {

        Mono.just("input")
                .flatMap(next -> someFunctionThatReturnsError())
                .doOnError(err -> System.err.println("Emitted onError: " + err.getMessage()))
                .onErrorResume(IllegalArgumentException.class, err -> fallbackValue())
                .onErrorMap(IOException.class, err -> new RuntimeException("Error on some function " + err.getMessage(), err))
                .doOnNext(next -> System.out.println("Emitted onNext: " + next))
                .doOnError(err -> System.err.println("Emitted onError: " + err.getMessage()))
                .subscribe(
                        next -> System.out.println("Received onNext: " + next),
                        err -> System.out.println("Received onError " + err.getMessage()),
                        () -> System.out.println("Received onComplete")
                );

    }

    private static Mono<Object> someFunctionThatReturnsError() {
        return Mono.just(Math.random())
                .filter(n -> n > 0.5)
                .flatMap(n -> Mono.error(() -> new IOException("Some IO exception encountered")))
                .switchIfEmpty(Mono.error(() -> new IllegalArgumentException("Some IllegalArgument exception encountered")));
    }

    private static Mono<String> fallbackValue() {
        return Mono.just("Fallback output");
    }

}
