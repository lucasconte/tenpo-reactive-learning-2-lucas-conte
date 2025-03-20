package cl.tenpo.learning.reactive.modules.module2.sec02_operators;

import cl.tenpo.learning.reactive.utils.CourseUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class Lec04ConcatMap {

    public static void main(String[] args) {

        Flux.just("Argentina", "Chile", "Perú", "Brasil", "Colombia")
                .concatMap(country -> getCurrencyByCountry(country)) // Mantiene el orden
                .subscribe(CourseUtils.subscriber());

        CourseUtils.sleepSeconds(5);
    }

    private static Mono<String> getCurrencyByCountry(String country) {

        return Mono.just("Moneda de " + country)
                .delayElement(Duration.ofMillis(CourseUtils.faker().random().nextInt(100, 1000))); // Retraso aleatorio

    }

}
