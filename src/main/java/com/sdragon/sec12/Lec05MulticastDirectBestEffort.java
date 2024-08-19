package com.sdragon.sec12;

import com.sdragon.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Sinks;

import java.time.Duration;

public class Lec05MulticastDirectBestEffort {

    private static final Logger log = LoggerFactory.getLogger(Lec05MulticastDirectBestEffort.class);

    public static void main(String[] args) {
        demo3();

        Util.sleepSeconds(10);
    }

    /*
     * demo1 problem
     * Sam is fast, but Mike is slow.
     * But the problem is that Sam is influenced by the mike and is slow together.
     * How can we solve this problem?
     */
    private static void demo1() {
        System.setProperty("reactor.bufferSize.small", "16");

        var sink = Sinks.many().multicast().onBackpressureBuffer(100);

        var flux = sink.asFlux();

        flux.subscribe(Util.subscriber("sam")); //very fast
        flux.delayElements(Duration.ofMillis(200)).subscribe(Util.subscriber("mike")); //very slow

        for (int i = 1; i <= 100; i++) {
            var result = sink.tryEmitNext(i);
            log.info("item: {}, result: {}", i, result);
        }
    }

    /*
     * demo2 solve problem
     * Mike loses the message, but Sam will get all the messages unaffected by Mike.
     */
    private static void demo2() {
        System.setProperty("reactor.bufferSize.small", "16");

        var sink = Sinks.many().multicast().directBestEffort();

        var flux = sink.asFlux();

        flux.subscribe(Util.subscriber("sam")); //very fast
        flux.delayElements(Duration.ofMillis(200)).subscribe(Util.subscriber("mike"));

        for (int i = 1; i <= 100; i++) {
            var result = sink.tryEmitNext(i);
            log.info("item: {}, result: {}", i, result);
        }
    }
    /*
     * If you don't want to lose Mike's message,you can use BackPressure to handle it.
     * If so, Sam and Mike may not lose their messages without affecting each other.
     */
    private static void demo3() {
        System.setProperty("reactor.bufferSize.small", "16");

        var sink = Sinks.many().multicast().directBestEffort();

        var flux = sink.asFlux();

        flux.subscribe(Util.subscriber("sam")); //very fast
        flux.onBackpressureBuffer().delayElements(Duration.ofMillis(200)).subscribe(Util.subscriber("mike"));

        for (int i = 1; i <= 100; i++) {
            var result = sink.tryEmitNext(i);
            log.info("item: {}, result: {}", i, result);
        }
    }
}
