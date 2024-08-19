package com.sdragon.sec12;

import com.sdragon.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Sinks;

/*
 * Late subscriber can see fast message
 */
public class Lec07Replay {

    private static final Logger log = LoggerFactory.getLogger(Lec07Replay.class);

    public static void main(String[] args) {
        demo1();
    }

    private static void demo1() {
//        var sink = Sinks.many().replay().limit(1);
        var sink = Sinks.many().replay().all();

        var flux = sink.asFlux();

        flux.subscribe(Util.subscriber("sam"));
        flux.subscribe(Util.subscriber("mike"));

        sink.tryEmitNext("hi");
        sink.tryEmitNext("how are you");
        sink.tryEmitNext("?");

        Util.sleepSeconds(2);

        flux.subscribe(Util.subscriber("jake"));
        sink.tryEmitNext("new Message");

    }
}
