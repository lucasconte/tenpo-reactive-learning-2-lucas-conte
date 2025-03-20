package cl.tenpo.learning.reactive.modules.module2.sec04_errors;

import cl.tenpo.learning.reactive.utils.CourseUtils;
import reactor.core.publisher.Mono;

public class Lec02OnErrorResume {

    public static void main(String[] args) {

        Mono.just("Hello")
                .flatMap(next -> someFunctionThatReturnsError())
                .onErrorResume(err -> fallbackPublisher())
                .subscribe(
                        next -> System.out.println("Received onNext: " + next),
                        err -> System.err.println("Received onError: " + err.getMessage())
                );

        CourseUtils.sleepSeconds(5);

    }

    private static Mono<String> someFunctionThatReturnsError() {
        return Mono.error(() -> new RuntimeException("oops! server unavailable"));
    }

    private static Mono<String> fallbackPublisher() {
        return Mono.just("Fallback using a Publisher");
    }

}
