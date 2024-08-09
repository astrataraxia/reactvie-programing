package com.sdragon.sec02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class MonoSubscribe {

    private static final Logger log = LoggerFactory.getLogger(MonoSubscribe.class);

    public static void main(String[] args) {
        var mono = Mono.just(1)
                .map(i -> i / 0);

        // You can just define the request, error, complete, and subscription directly through the subscription method defined in Mono.
        // You can also define it or not. If an error is generated when a request is made without defining an error, the onError set to default is drpo.
        mono.subscribe(
                i -> log.info("received: {}", i),
                err -> log.error("error", err),
                () -> log.info("completed"),
                subscription -> subscription.request(1)
        );
    }
}
