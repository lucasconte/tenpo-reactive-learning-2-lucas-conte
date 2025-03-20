package cl.tenpo.learning.reactive.modules.module2.sec04_errors;

import cl.tenpo.learning.reactive.utils.CourseUtils;
import reactor.core.publisher.Mono;

public class Lec01OnErrorReturn {

    public static void main(String[] args) {

        Mono.just("Hello")
                .flatMap(next -> someFunctionThatReturnsError())
                .doOnError(err -> System.out.println("Emitted onError: " + err.getMessage()))
                .onErrorReturn("Fallback constant")
                .subscribe(
                        next -> System.out.println("Received onNext: " + next),
                        err -> System.err.println("Received onError:" + err)
                );

        CourseUtils.sleepSeconds(5);

    }

    private static Mono<String> someFunctionThatReturnsError() {
        return Mono.error(() -> new RuntimeException("oops! server unavailable"));
    }

}
