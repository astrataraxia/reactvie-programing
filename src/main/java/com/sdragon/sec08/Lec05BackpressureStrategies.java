package com.sdragon.sec08;

import com.sdragon.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/*
 * Buffer strategy : Keep them all in memory
 *  - It is a useful strategy when the data produced by producers is sometimes oversupplied.
 *  - The consumer always processes at a constant speed.
 *  - The buffer accumulates data produced by the producer. Therefore, it is useful when data enters excessively
 *
 *  Error strategy : notify downstream that it is too slow
 *   - The error strategy only produces the size of the buffer.
 *   - If you produce more than the size of that buffer, you drop the error immediately.
 *
 *  Mix strategy
 *   - It is same to use buffer strategy (onBackpressureBuffer()).
 *   - However, I can decide the size of the limit buffer here.
 *   - If you produce more than the size of the buffer, it throws an error.
 *
 * Drop strategy : drop the items once the internal queue is full or drop any items which are not requested
 *  - The drop strategy is to send the information requested by the subscriber and drop the remaining information.
 *  - The information produced by the producer is so fast that it keeps piling up in the buffer. 1, 2, 3, 4, 5 ...
 *  - If you request one piece of information at this time, you send 1 and drop all the rest.
 *
 *
 * Latest strategy : same as drop but keep 1 latest item to be safe
 *  - It is almost same drop strategy. but one small different
 *  - As the late name suggests, when the publisher generates the latest information,
 *  - the previous information is dropped. This is why you always keep the latest information.
 *
 * Overflow strategy
 *  - This does not add a backpressure strategy as an operator,
 *  - but rather allows publishers to select and send a backpressure strategy.
 *  - giving one strategy all subscriber.
 */

public class Lec05BackpressureStrategies {

    private static final Logger log = LoggerFactory.getLogger(Lec05BackpressureStrategies.class);

    public static void main(String[] args) {



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
//                .onBackpressureBuffer()  - buffer strategy
//                .onBackpressureError()  - Error strategy
//                .onBackpressureBuffer(10)  - Buffer and Error mix
//                .onBackpressureDrop()   - Drop strategy
                .onBackpressureLatest()  // - Latest strategy
                .log()
                .limitRate(1)
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
