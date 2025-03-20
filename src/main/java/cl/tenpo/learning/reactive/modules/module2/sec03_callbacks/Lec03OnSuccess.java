package cl.tenpo.learning.reactive.modules.module2.sec03_callbacks;

import cl.tenpo.learning.reactive.utils.CourseUtils;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Signal;

public class Lec03OnSuccess {

    public static void main(String[] args) {

        Mono.just("Hello")
                .doOnNext(next -> System.out.println("Emitted onNext: " + next))
                .doOnSuccess(next -> System.out.println("onSuccess: " + next))
                .map(next -> next.concat(" World!"))
                .doOnNext(next -> System.out.println("Emitted onNext: " + next))
                .doOnSuccess(next -> System.out.println("OnSuccess: " + next))
                .subscribe(
                        next -> System.out.println("Received onNext: " + next),
                        err -> System.err.println("Received onError: " + err.getMessage()),
                        () -> System.out.println("Received onComplete")
                );

        CourseUtils.sleepSeconds(5);

    }

}
