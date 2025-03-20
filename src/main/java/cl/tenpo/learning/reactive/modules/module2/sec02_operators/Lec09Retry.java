package cl.tenpo.learning.reactive.modules.module2.sec02_operators;

import cl.tenpo.learning.reactive.utils.CourseUtils;
import reactor.core.publisher.Flux;

public class Lec09Retry {

    public static void main(String[] args) {

        Flux.error(new RuntimeException("Api Error!"))
                .log()
                .retry(2)
                .subscribe(CourseUtils.subscriber("retry-sub"));

    }

}
