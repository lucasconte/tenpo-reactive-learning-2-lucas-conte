package cl.tenpo.learning.reactive.modules.module2.sec03_callbacks;

import cl.tenpo.learning.reactive.utils.CourseUtils;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class Lec03OnSuccessEmpty {

    public static void main(String[] args) {

        Mono.empty()
                .doOnSuccess(next -> log.info("OnSuccess: {}", next))
                .subscribe(CourseUtils.subscriber());

        CourseUtils.sleepSeconds(5);

    }

}
