package cl.tenpo.learning.reactive.modules.module2.sec03_callbacks;

import cl.tenpo.learning.reactive.utils.CourseUtils;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Signal;

@Slf4j
public class Lec03OnSuccess {

    public static void main(String[] args) {

        Mono.just("Hello")
                .doOnNext(next -> log.info("Emitted onNext: {}", next))
                .doOnSuccess(next -> log.info("onSuccess: {}", next))
                .map(next -> next.concat(" World!"))
                .doOnNext(next -> log.info("Emitted onNext: {}", next))
                .doOnSuccess(next -> log.info("OnSuccess: {}", next))
                .subscribe(CourseUtils.subscriber());

        CourseUtils.sleepSeconds(5);

    }

}
