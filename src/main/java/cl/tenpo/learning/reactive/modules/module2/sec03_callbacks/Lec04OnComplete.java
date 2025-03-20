package cl.tenpo.learning.reactive.modules.module2.sec03_callbacks;

import cl.tenpo.learning.reactive.utils.CourseUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Lec04OnComplete {

    public static void main(String[] args) {

        Flux.just("Hello", "World", "!")
                .doOnNext(next -> System.out.println("Emitted onNext: " + next))
                .doOnComplete(() -> System.out.println("Emitted onComplete"))
                .subscribe(
                        next -> System.out.println("Received onNext: " + next),
                        err -> System.err.println("Received onError: " + err.getMessage()),
                        () -> System.out.println("Received onComplete")
                );

        CourseUtils.sleepSeconds(5);

    }

}
