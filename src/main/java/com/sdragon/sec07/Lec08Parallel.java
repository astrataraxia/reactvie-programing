package com.sdragon.sec07;

import com.sdragon.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Lec08Parallel {

    private static final Logger log = LoggerFactory.getLogger(Lec08Parallel.class);

    public static void main(String[] args) {
        Flux.range(1, 10)
                .parallel()
                .runOn(Schedulers.parallel())
                .map(i -> process(i))
//                .sequential()
                .map(i -> i + "a" )
                .subscribe(Util.subscriber());

        Util.sleepSeconds(5);
    }

    private static int process(int i) {
        log.info("time consuming task {}", 1);
        Util.sleepSeconds(1);
        return i * 2;
    }
}
