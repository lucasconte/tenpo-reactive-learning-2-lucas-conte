package cl.tenpo.learning.reactive.modules.module2.sec04_errors;

import reactor.core.publisher.Mono;

import java.io.IOException;

public class Lec03OnErrorMap {

    public static void main(String[] args) {

        Mono.just("input")
                .flatMap(next -> someFunctionThatReturnsError())
                .doOnError(err -> System.err.println("Error class: " + err.getClass().getSimpleName()))
                .onErrorMap(err -> new IOException("Encountered error running some function -> " + err.getMessage(), err))
                .doOnError(err -> System.err.println("Error class: " + err.getClass().getSimpleName()))
                .subscribe(
                        next -> System.out.println("Received onNext: " + next),
                        err -> System.err.println("Received onError: " + err.getMessage())
                );


    }

    private static Mono<String> someFunctionThatReturnsError() {
        return Mono.error(() -> new RuntimeException("oops! server unavailable"));
    }

}
