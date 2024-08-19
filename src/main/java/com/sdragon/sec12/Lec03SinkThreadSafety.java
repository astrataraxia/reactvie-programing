package com.sdragon.sec12;

import com.sdragon.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Sinks;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class Lec03SinkThreadSafety {

    private static final Logger log = LoggerFactory.getLogger(Lec03SinkThreadSafety.class);

    public static void main(String[] args) {
        demo2();
    }


    private static void demo1() {
        var sink = Sinks.many().unicast().onBackpressureBuffer();

        var flux = sink.asFlux();
        /*
         * Is sink thread safe?
         * - The reactor document says sink is thread safe, but it is not
         *   In fact, it's safe from threads, but the point here is that it's not synchronized internally.
         *   Sink is secure access from external threads, but is not internally synchronized.
         *   Problems can occur when multiple threads try to access sink at the same time.
         *   Therefore, it is our responsibility to solve this problem using emitter error.
         */
        var list = new ArrayList<>();
        flux.subscribe(list::add);

        for (int i = 0; i < 1000; i++) {

            var j = i;
            CompletableFuture.runAsync(() -> {
                sink.tryEmitNext(j);
            });
        }

        Util.sleepSeconds(2);

        log.info("list size: {}", list.size());
    }

    private static void demo2() {
        var sink = Sinks.many().unicast().onBackpressureBuffer();

        var flux = sink.asFlux();

        var list = new ArrayList<>();
        flux.subscribe(list::add);

        for (int i = 0; i < 1000; i++) {
            var j = i;
            CompletableFuture.runAsync(() -> {
                sink.emitNext(j, (signal, emitResult) -> {
                    return Sinks.EmitResult.FAIL_NON_SERIALIZED.equals(emitResult);
                });
            });
        }

        Util.sleepSeconds(2);

        log.info("list size: {}", list.size());
    }
}
