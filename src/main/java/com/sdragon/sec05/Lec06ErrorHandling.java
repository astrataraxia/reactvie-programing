package com.sdragon.sec05;

import com.sdragon.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/*
 * How to handle error in a reactive programing
 * Flux.(...)
 *    ...
 *    ...
 *    ...
 */
public class Lec06ErrorHandling {
    private static final Logger log = LoggerFactory.getLogger(Lec06ErrorHandling.class);

    public static void main(String[] args) {
        Flux.range(1, 10)
                .map(i -> i == 5 ? 5/0 : i) //intentional
                .onErrorContinue((ex, obj) -> log.error("==> {}", obj, ex))
                .subscribe(Util.subscriber());

    }

    private static void onErrorReturn() {
        // It is same to run Flux. ex) Flux.range(1,10)...
        // when you want to return a hardcoded value / simple computation
        Mono.just(5)
                .map(i -> i == 5 ? 5/0 : i) //intentional
                .onErrorReturn(IllegalAccessError.class, -1)
                .onErrorReturn(ArithmeticException.class, -2)
                .onErrorReturn(-3)
                .subscribe(Util.subscriber());
    }

    private static void onErrorResume() {
        Mono.error(new RuntimeException("oops"))
                .onErrorResume(ArithmeticException.class, ex -> fallback())
                .onErrorResume(ex -> fallback2())
                .onErrorReturn(-5)
//                .onErrorResume(ex -> fallback())
                .subscribe(Util.subscriber());
    }

    private static void onErrorComplete() {
        Mono.just(1)
                .onErrorComplete()
                .subscribe(Util.subscriber());
    }

    private static Mono<Integer> fallback() {
        return Mono.fromSupplier(() -> Util.faker().random().nextInt(10, 100));
    }
    private static Mono<Integer> fallback2() {
        return Mono.fromSupplier(() -> Util.faker().random().nextInt(100, 1000));
    }
}
