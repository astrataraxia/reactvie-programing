package com.sdragon.sec04;

import com.sdragon.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Lec06FluxGenerate {

    private static final Logger log = LoggerFactory.getLogger(Lec06FluxGenerate.class);

    // generate SynchronousSink is emit only one value
    // Generate have loop, so if there is no request for termination, loop is continuously executed for one request.
    // emit one value and it will be invoked again and again based on the downstream demand.
    public static void main(String[] args) {
        synchronousSinkTest();
    }

    private static void synchronousSinkTest() {
        Flux.generate(synchronousSink -> {
                    log.info("invoked");
                    synchronousSink.next(1);
                })
                .take(4)
                .subscribe(Util.subscriber());
    }

}
