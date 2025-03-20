package cl.tenpo.learning.reactive.modules.module2.sec04_errors;

import cl.tenpo.learning.reactive.utils.CourseUtils;
import reactor.core.publisher.Mono;

public class Lec04OnErrorComplete {

    public static void main(String[] args) {

        Mono.just("input")
                .flatMap(next -> someFunctionThatReturnsError())
                .doOnError(err -> System.err.println("Emitted onError: " + err.getMessage()))
                .onErrorComplete()
                .subscribe(
                        next -> System.out.println("Received onNext: " + next),
                        err -> System.err.println("Received onError: " + err.getMessage()),
                        () -> System.out.println("Received onComplete")
                );

        CourseUtils.sleepSeconds(5);

    }

    private static Mono<String> someFunctionThatReturnsError() {
        return Mono.error(() -> new RuntimeException("oops! server unavailable"));
    }

}
