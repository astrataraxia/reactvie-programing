package com.sdragon.sec02;

import com.sdragon.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

public class Lec08MonoFromFuture {

    private static final Logger log = LoggerFactory.getLogger(Lec08MonoFromFuture.class);

    public static void main(String[] args) {
        // if do not have subscribe, but it's doing the work if you see. this could be a problem
        // so you can use lamda, CompletableFuture -> supplier
        Mono.fromFuture(() -> getName());
//                .subscribe(Util.subscriber());

        Util.sleepSeconds(1);
    }

    // completableFuture is not lazy. even though it accepts supplier here, it is not lazy
    // When you try to create the  CompletableFuture object, it will do this execution almost "immediately"
    // reactive programing, everything should be lazy as much as possible
    private static CompletableFuture<String> getName() {
        return CompletableFuture.supplyAsync(() -> {
            log.info("generating name");
            return Util.faker().name().firstName();
        });
    }
}
