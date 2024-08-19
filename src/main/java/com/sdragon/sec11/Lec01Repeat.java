package com.sdragon.sec11;

import com.sdragon.common.Util;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * repeat operator simply resubscribes when it sees complete signal.
 * it does not like error signal.
 */
public class Lec01Repeat {

    public static void main(String[] args) {
        // repeat : mono -> flux change
        /*
         * If you use the loop, send a simultaneous request before a response comes.
         * With repeat, you send the next signal only after you completely check the signal.
         */

        demo4();
        Util.sleepSeconds(10);

    }

    private static void demo1() {
        getCountryName()
                .repeat(3)
                .subscribe(Util.subscriber());
    }

    private static void demo2() {
        getCountryName()
                .repeat()
                .takeUntil(c -> c.equalsIgnoreCase("canada"))
                .subscribe(Util.subscriber());
    }

    private static void demo3() {
        var atomicInteger = new AtomicInteger(0);
        getCountryName()
                .repeat(() -> atomicInteger.incrementAndGet() < 3)
                .subscribe(Util.subscriber());
    }

    private static void demo4() {
        getCountryName()
                .repeatWhen(flux -> flux.delayElements(Duration.ofSeconds(1)).take(2))
                .subscribe(Util.subscriber());
    }

    private static Mono<String> getCountryName() {
        return Mono.fromSupplier(() -> Util.faker().country().name()); //non blocking IO
    }
}
