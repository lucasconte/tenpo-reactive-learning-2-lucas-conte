package cl.tenpo.learning.reactive.modules.module2.sec03_callbacks;

import cl.tenpo.learning.reactive.utils.CourseUtils;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class Lec01OnNextFlux {

    public static void main(String[] args) {

        Flux.just("Hello", "World", "!")
                .doOnNext(next -> log.info("Emitted onNext: {}", next))
                .subscribe(CourseUtils.subscriber());

        CourseUtils.sleepSeconds(5);

    }

}
