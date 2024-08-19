package com.sdragon.sec12;

import com.sdragon.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Sinks;

public class Lec04Multicast {
    private static final Logger log = LoggerFactory.getLogger(Lec04Multicast.class);

    public static void main(String[] args) {
        demo2();
    }


    private static void demo1() {
        // handle through which we would push items
        // onBackPressureBuffer - bounded queue
        var sink = Sinks.many().multicast().onBackpressureBuffer();

        //handle through which subscribers will receive item
        var flux = sink.asFlux();

        flux.subscribe(Util.subscriber("sam"));
        flux.subscribe(Util.subscriber("mike"));

        sink.tryEmitNext("hi");
        sink.tryEmitNext("how are you");
        sink.tryEmitNext("?");

        Util.sleepSeconds(2);

        // late subscriber
        flux.subscribe(Util.subscriber("jake"));
        sink.tryEmitNext("new Message");

    }

    // warm up
    /*
     * Warmup has a message in memory (a certain amount),
     * and gives the message to the first subscriber and empties it.
     * Those who come afterwards do not receive the previous message.
     */
    private static void demo2() {

        var sink = Sinks.many().multicast().onBackpressureBuffer();

        var flux = sink.asFlux();

        sink.tryEmitNext("hi");
        sink.tryEmitNext("how are you");
        sink.tryEmitNext("?");

        Util.sleepSeconds(2);

        flux.subscribe(Util.subscriber("sam"));
        flux.subscribe(Util.subscriber("mike"));
        flux.subscribe(Util.subscriber("jake"));
        sink.tryEmitNext("new Message");

    }
}
