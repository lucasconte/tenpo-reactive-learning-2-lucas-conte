package cl.tenpo.learning.reactive.modules.module2.sec03_callbacks;

import cl.tenpo.learning.reactive.utils.CourseUtils;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Signal;

public class Lec05OnEach {

    public static void main(String[] args) {

        Mono.just("Hello")
                .map(next -> next.concat(" World!"))
                .doOnEach(Lec05OnEach::logOnEach)
                .subscribe(
                        next -> System.out.println("Received onNext: " + next),
                        err -> System.err.println("Received onError: " + err.getMessage()),
                        () -> System.out.println("Received onComplete")
                );

        CourseUtils.sleepSeconds(5);

    }

    private static void logOnEach(Signal<String> signal) {
        if (signal.isOnComplete()) {
            System.out.println("Signal value is null -> " + signal.get());
            System.out.println("Emitted onComplete");
        } else if(signal.isOnError()) {
            System.err.println("Emitted onError " + signal.get());
        } else if(signal.isOnNext()) {
            System.out.println("Emitted onNext " + signal.get());
        }
    }

}
