package com.sdragon.sec02;

import com.sdragon.sec01.subscriber.SubscriberImpl;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class MonoJust {

    private static final Logger log = LoggerFactory.getLogger(MonoJust.class);

    public static void main(String[] args) {
        // easy to make publisher. It doesn't give the actual value.
        // Till then we will not be getting the value from the publisher. we have to subscribe.
        var mono = Mono.just("vins");

        // it work. but It doesn't print the value now. why?
        // Because if you remember, our subscriber subscribes, but it has not yet requested.
        // subscribe and request. so subscriber get subscription.
        var subscriber = new SubscriberImpl();
        mono.subscribe(subscriber);

        subscriber.getSubscription().request(10);
        // next two step is not work. because mono is just 0 or 1 request.
        subscriber.getSubscription().request(10);
        subscriber.getSubscription().cancel();
    }
}
