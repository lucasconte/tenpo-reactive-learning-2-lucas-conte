package cl.tenpo.learning.reactive.modules.module2.sec03_callbacks;

import cl.tenpo.learning.reactive.utils.CourseUtils;
import reactor.core.publisher.Mono;

public class Lec02OnError {

    public static void main(String[] args) {

        Mono.just("GoodBye")
                .doOnNext(next -> System.out.println("Emitted onNext: " + next))
                .flatMap(Lec02OnError::buildFinalMessage)
                .doOnNext(next -> System.out.println("Emitted onNext: " + next))
                .doOnError(err -> System.err.println("Emitted onError: " + err.getMessage()))
                .subscribe(
                        next -> System.out.println("Received onNext: " + next),
                        err -> System.err.println("Received onError: " + err.getMessage())
                );

        CourseUtils.sleepSeconds(5);

    }

    private static Mono<String> buildFinalMessage(String input) {
        return Mono.just(input)
                .filter(next -> next.equals("Hello"))
                .switchIfEmpty(Mono.error(() -> new RuntimeException("Input can only be Hello. Input: " + input)));
    }

}
