package com.sdragon.sec02;

import com.sdragon.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.awt.event.WindowFocusListener;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class Lec09PublisherCreateVsExecution {

    private static final Logger log = LoggerFactory.getLogger(Lec09PublisherCreateVsExecution.class);

    public static void main(String[] args) {
        getName();
//                .subscribe(Util.subscriber());
    }

    // Create and execution is different.
    private static Mono<String> getName() {
        log.info("entered the method");
        return Mono.fromSupplier(() -> {
            Util.sleepSeconds(3);
            log.info("generating name");
            return Util.faker().name().firstName();
        });


//        Anonymous function
//        return Mono.fromSupplier(new Supplier<>() {
//            @Override
//            public String get() {
//                log.info("generating name");
//                return Util.faker().name().firstName();
//            }
//        });
    }
}
