package cl.tenpo.learning.reactive.modules.module2.sec03_callbacks;

import cl.tenpo.learning.reactive.utils.CourseUtils;
import reactor.core.publisher.Mono;

public class Lec03OnSuccessEmpty {

    public static void main(String[] args) {

        Mono.empty()
                .doOnSuccess(next -> System.out.println("OnSuccess: " + next))
                .subscribe(
                        next -> System.out.println("Received onNext: " + next),
                        err -> System.err.println("Received onError: " + err.getMessage()),
                        () -> System.out.println("Received onComplete")
                );

        CourseUtils.sleepSeconds(5);

    }

}
