package cl.tenpo.learning.reactive.modules.module2.sec03_callbacks;

import cl.tenpo.learning.reactive.utils.CourseUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Lec01OnNextFlux {

    public static void main(String[] args) {

        Flux.just("Hello", "World", "!")
                .doOnNext(next -> System.out.println("Emitted onNext: " + next))
                .subscribe(
                        next -> System.out.println("Received onNext: " + next)
                );

        CourseUtils.sleepSeconds(5);

    }

}
