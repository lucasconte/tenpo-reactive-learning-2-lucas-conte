package cl.tenpo.learning.reactive.modules.module2.sec05_schedulers;

import cl.tenpo.learning.reactive.modules.module2.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;


public class Scheduler {

    public static void main(String[] args) {
        //schedulerInmediate();
        //schedulerSingle();
        //schedulerParallel();
        //schedulerBoundedElastic();
        subscribeOnPublishOnExample();
    }

    public static void schedulerInmediate() {
        Mono.just("A")
                .subscribeOn(Schedulers.immediate())
                .log()
                .subscribe(Util.subscriber());
    }

    public static void schedulerSingle() {
        Mono.just("A")
                .subscribeOn(Schedulers.single())
                .log()
                .subscribe(Util.subscriber());
    }

    public static void schedulerParallel() {
        Flux.range(1, 10)
                .map(i -> i * 2)
                .subscribeOn(Schedulers.parallel())
                .log()
                .subscribe(Util.subscriber());

        Util.sleepSeconds(5);

    }

    public static void schedulerBoundedElastic() {
        Mono.fromCallable(() -> "Llamada bloqueante")
                .log()
                .subscribeOn(Schedulers.boundedElastic())
                .log()
                .subscribe(Util.subscriber());

        Util.sleepSeconds(5);

    }

    public static void subscribeOnPublishOnExample() {
        Flux.range(1, 5)
                .doOnNext(i -> Util.log("Generando número: " + i))
                .subscribeOn(Schedulers.boundedElastic())
                .map(i -> {
                    Util.log("Multiplicando: " + i);
                    return i * 10;
                })
                .publishOn(Schedulers.parallel())
                .map(i -> {
                    Util.log("Operación pesada: " + i);
                    return i + " procesado";
                })
                .subscribe(result -> Util.log("Recibido: " + result));

        Util.sleepSeconds(15);
    }
}


