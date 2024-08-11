package com.sdragon.sec04;

import com.sdragon.common.Util;
import com.sdragon.sec01.subscriber.SubscriberImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Lec04FluxCreateDownstreamDemand {

    /**
     * IMPORTANT!!!! - Flux.create is designed to be used when we have a single subscriber.
     * FluxSink is thread safe. we can share it with multiple threads.
     * We can keep on emitting data into the sink w/o worrying about downstream demand
     * FluxSink will deliver everything to Subscriber safely
     */

    private static final Logger log = LoggerFactory.getLogger(Lec04FluxCreateDownstreamDemand.class);

    public static void main(String[] args) {
        // Flux.create producer produces all the items upfront
        // Most of case, lazy is actually good. but some cases doing all the work upfront then that can also be efficient
        // produceEarly();
        produceOnDemand();

    }

    private static void produceEarly() {
        var subscriber = new SubscriberImpl();
        Flux.<String>create(fluxSink -> {
            for (int i = 0; i < 10; i++) {
                var name = Util.faker().name().firstName();
                log.info("generated: {}", name);
                fluxSink.next(name);
            }
            fluxSink.complete();
        }).subscribe(subscriber);

        Util.sleepSeconds(2);
        subscriber.getSubscription().request(2);
        Util.sleepSeconds(2);
        subscriber.getSubscription().request(2);
        Util.sleepSeconds(2);
        subscriber.getSubscription().cancel();
        subscriber.getSubscription().request(2);
    }

    private static void produceOnDemand() {
        var subscriber = new SubscriberImpl();
        Flux.<String>create(fluxSink -> {
            fluxSink.onRequest(request -> {
                for (int i = 0; i < request && !fluxSink.isCancelled(); i++) {
                    var name = Util.faker().name().firstName();
                    log.info("generated: {}", name);
                    fluxSink.next(name);
                }
            });
        }).subscribe(subscriber);

        Util.sleepSeconds(2);
        subscriber.getSubscription().request(2);
        Util.sleepSeconds(2);
        subscriber.getSubscription().request(2);
        Util.sleepSeconds(2);
        subscriber.getSubscription().cancel();
        subscriber.getSubscription().request(2);
    }
}
