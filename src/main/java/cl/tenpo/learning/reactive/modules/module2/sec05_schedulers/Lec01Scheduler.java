package cl.tenpo.learning.reactive.modules.module2.sec05_schedulers;

import cl.tenpo.learning.reactive.utils.CourseUtils;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Lec01Scheduler {

    public static void main(String[] args) {

        Flux.range(1, 5)
                .doOnNext(i -> CourseUtils.log("Generando número: " + i))
                .subscribeOn(Schedulers.boundedElastic())
                .map(i -> {
                    CourseUtils.log("Multiplicando: " + i);
                    return i * 10;
                })
                .publishOn(Schedulers.parallel())
                .map(i -> {
                    CourseUtils.log("Operación pesada: " + i);
                    return i + " procesado";
                })
                .subscribe(result -> CourseUtils.log("Recibido: " + result));

        CourseUtils.sleepSeconds(10);

    }

}
