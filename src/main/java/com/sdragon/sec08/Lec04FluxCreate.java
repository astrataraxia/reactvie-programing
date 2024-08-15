package com.sdragon.sec08;

import com.sdragon.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Lec04FluxCreate {

    /**
     * What happens if we don't do backpressure treatment?
     * I'm going to try using Flux create because it's completely different.
     * we will have manage to self, if using flux.create().
     */
    private static final Logger log = LoggerFactory.getLogger(Lec04FluxCreate.class);

    public static void main(String[] args) {

        System.setProperty("reactor.bufferSize.small", "16");

        var producer = Flux.create(sink -> {
                    for (int i = 1; i <= 500 && !sink.isCancelled(); i++) {
                        log.info("generating {}", i);
                        sink.next(i);
                        Util.sleepMillis(50);
                    }
                    sink.complete();
                })
                .cast(Integer.class)
                .subscribeOn(Schedulers.parallel());

        producer
//                .limitRate(5) This doesn't really solve the problem. It makes it worse. It reduces the size of the buffer, but it doesn't have back pressure, so it receives less because of the reduced size of the buffer.
                .publishOn(Schedulers.boundedElastic())
                .map(i -> timeConsumingTask(i))
                .subscribe();

        Util.sleepSeconds(60);
    }

    private static int timeConsumingTask(int i) {
        log.info("receiving {}", i);
        Util.sleepSeconds(1);
        return i;
    }
}
