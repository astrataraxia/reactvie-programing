package com.sdragon.sec10;

import com.sdragon.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class Lec03Window {

    public static void main(String[] args) {
        eventStream()
                .window(Duration.ofMillis(1800))
                .flatMap(flux -> processEvent(flux))
                .subscribe();

        Util.sleepSeconds(60);
    }

    private static Flux<String> eventStream() {
        return Flux.interval(Duration.ofMillis(500))
                .map(i -> "event-" + i);
    }

    private static Mono<Void> processEvent(Flux<String> flux) {
        return flux.doOnNext(e -> System.out.print("*"))
                .doOnComplete(System.out::println)
                .then();
    }

}
