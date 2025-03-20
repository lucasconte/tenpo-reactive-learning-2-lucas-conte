package cl.tenpo.learning.reactive.modules.module2.sec02_operators;

import cl.tenpo.learning.reactive.utils.CourseUtils;
import reactor.core.publisher.Flux;

public class Lec01Map {

    public static void main(String[] args) {
        Flux.<String>create(fluxSink -> {
                    for (int i = 0; i < 10; i++) {
                        fluxSink.next(CourseUtils.faker().country().name().toString());
                    }
                    fluxSink.complete();
                })
                .map(String::toUpperCase)
                .subscribe(CourseUtils.subscriber());
    }

}
