package cl.tenpo.learning.reactive.modules.module2.sec02_operators;

import cl.tenpo.learning.reactive.modules.module2.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class Operators {

    public static void main(String[] args) {
        map();
        //filter();
        //flatMap();
        //concatMap();
        //then();
        //thenMany();
        //defaultIfEmptyExample();
        //switchIfEmptyExample();
        //repeat();
        //retry();
        //timeOut();
        //zip();
        //merge();
    }

    public static void map() {
        Flux.<String>create(fluxSink -> {
                    for (int i = 0; i < 10; i++) {
                        fluxSink.next(Util.faker().country().name().toString());
                    }
                    fluxSink.complete();
                })
                .map(String::toUpperCase)
                .subscribe(Util.subscriber());
    }

    public static void filter() {
        Flux.<String>generate(synchronousSink ->
                        synchronousSink.next(Util.faker().country().name()))
                .filter(s -> s.startsWith("A"))
                .take(10)
                .subscribe(Util.subscriber());
    }

    public static void flatMap() {
        Flux.just("Argentina", "Chile", "Perú", "Brasil", "Colombia")
                .flatMap(country -> getCurrencyByCountry(country)) // No mantiene el orden
                .subscribe(Util.subscriber());

        Util.sleepSeconds(5);
    }

    public static void concatMap() {
        Flux.just("Argentina", "Chile", "Perú", "Brasil", "Colombia")
                .concatMap(country -> getCurrencyByCountry(country)) // Mantiene el orden
                .subscribe(Util.subscriber());

        Util.sleepSeconds(5);
    }

    public static void then() {
        Flux.just("Argentina", "Chile", "Perú", "Brasil", "Colombia")
                .then(Mono.just("Paises procesados"))
                .subscribe(Util.subscriber());
    }

    public static void thenMany() {
        Mono.just("Argentina")
                .thenMany(Flux.just("Chile", "Peru", "Brasil", "Colombia"))
                .subscribe(Util.subscriber());
    }


    //Simulamos un servicio externo que nos devuelve la moneda de un país
    private static Mono<String> getCurrencyByCountry(String country) {
        return Mono.just("Moneda de " + country)
                .delayElement(Duration.ofMillis(Util.faker().random().nextInt(100, 1000))); // Retraso aleatorio
    }

    public static void defaultIfEmptyExample() {
        Flux<String> countriesFlux = Flux.empty();

        countriesFlux
                .defaultIfEmpty("Argentina")
                .subscribe(Util.subscriber());
    }

    public static void switchIfEmptyExample() {
        Flux<String> countriesFlux = Flux.empty();

        countriesFlux
                .switchIfEmpty(Flux.just("Argentina", "Chile", "Perú", "Brasil", "Colombia"))
                .subscribe(Util.subscriber());
    }

    public static void repeat() {
        Flux.just("Argentina", "Chile", "Perú", "Brasil", "Colombia")
                .repeat(3)
                .subscribe(Util.subscriber());
     }

    public static void retry() {
        Flux.error(new RuntimeException("Api Error!"))
                .log()
                .retry(2)
                .subscribe(Util.subscriber("retry-sub"));
    }

    public static void timeOut(){
        Flux.just("Argentina", "Chile", "Perú", "Brasil", "Colombia", "Venezuela", "Ecuador")
                .delayElements(Duration.ofSeconds(3))
                .timeout(Duration.ofSeconds(5))
                .subscribe(Util.subscriber());

        Util.sleepSeconds(25);
    }

    public static void zip() {
        Mono<String> userName = Mono.just("Juan Pérez").delayElement(Duration.ofMillis(500));
        Mono<Integer> userAge = Mono.just(30).delayElement(Duration.ofMillis(300));
        Mono<String> userCity = Mono.just("Buenos Aires").delayElement(Duration.ofMillis(700));

        Mono<String> userInfo = Mono.zip(userName, userAge, userCity)
                .map(tuple -> "User: " + tuple.getT1() + ", Edad: " + tuple.getT2() + ", Ciudad: " + tuple.getT3());

        userInfo.subscribe(Util.subscriber());

        Util.sleepSeconds(5);
    }

    public static void merge() {
            Mono<String> userName = Mono.just("Juan Pérez").delayElement(Duration.ofMillis(500));
            Mono<Integer> userAge = Mono.just(30).delayElement(Duration.ofMillis(300));
            Mono<String> userCity = Mono.just("Buenos Aires").delayElement(Duration.ofMillis(700));

            Flux<Object> mergedFlux = Flux.merge(userName, userAge, userCity);

            mergedFlux.subscribe(Util.subscriber());
            Util.sleepSeconds(5);
    }
}