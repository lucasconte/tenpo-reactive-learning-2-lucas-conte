package cl.tenpo.learning.reactive.modules.module2.sec01_monoflux;

import cl.tenpo.learning.reactive.modules.module2.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

public class MonoFlux {

    public static void main(String[] args) {
        /** Mono **/

        monoJust();
        //monoEmpty();
        //monoError();

        /** Flux **/

        //fluxJust();
        //fluxFromIterable();
        //fluxInterval();
        //fluxRange();
    }

    private static void monoJust() {
        Mono.just("Hola, Reactive!")
                .subscribe(Util.subscriber());
    }

    private static void monoEmpty() {
        Mono.empty()
                .subscribe(Util.subscriber());
    }

    private static void monoError() {
        Mono.error(new RuntimeException("error !!!!"))
                .subscribe(Util.subscriber());
    }

    private static void fluxJust() {
        Flux.just("Elemento 1", "Elemento 2", "Elemento 3")
                .subscribe(Util.subscriber());
    }

    private static void fluxFromIterable() {
        Flux.fromIterable(List.of(1, 2, 3, 4))
                .subscribe(Util.subscriber());
    }

    private static void fluxInterval() {
        Flux.interval(Duration.ofMillis(500))
                .subscribe(Util.subscriber());

        Util.sleepSeconds(15);
    }

    private static void fluxRange() {
        Flux.range(10, 5)
                .subscribe(Util.subscriber());
    }

}
