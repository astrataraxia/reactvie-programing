package com.sdragon.sec08;


import com.sdragon.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Lec03MultipleSubscribers {

    /*
     *  Even if there are multiple subscribers, the producer thinks at the pace of the consumer.
     *  Therefore, the fast one is fast, the slow one is slow.
     *  This means that the reactor library implements all the backpressure
     */

    private static final Logger log = LoggerFactory.getLogger(Lec03MultipleSubscribers.class);
    
    
    public static void main(String[] args) {
        var producer = Flux.generate(
                        () -> 1,
                        (state, sink) -> {
                            log.info("generating {}", state);
                            sink.next(state);
                            return ++state;
                        }
                )
                .cast(Integer.class)
                .subscribeOn(Schedulers.parallel());

        producer
                .limitRate(5)
                .publishOn(Schedulers.boundedElastic())
                .map(i -> timeConsumingTask(i))
                .subscribe(Util.subscriber("sub1"));

        // this subscriber is fast
        producer
                .take(100)
                .publishOn(Schedulers.boundedElastic())
                .subscribe(Util.subscriber("sub2"));

        Util.sleepSeconds(60);
    }

    private static int timeConsumingTask(int i) {
        Util.sleepSeconds(1);
        return i;
    }
}
