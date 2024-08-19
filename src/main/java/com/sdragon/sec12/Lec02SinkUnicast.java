package com.sdragon.sec12;

import com.sdragon.common.Util;
import reactor.core.publisher.Sinks;

public class Lec02SinkUnicast {

    public static void main(String[] args) {
        demo2();
    }

    private static void demo1() {
        // handle through which we would push items
        // onBackPressureBuffer - unbounded queue
        var sink = Sinks.many().unicast().onBackpressureBuffer();

        //handle through which subscribers will receive item
        var flux = sink.asFlux();

        sink.tryEmitNext("hi");
        sink.tryEmitNext("how are you");
        sink.tryEmitNext("?");

        flux.subscribe(Util.subscriber("sam"));
    }

    private static void demo2() {
        // handle through which we would push items
        // onBackPressureBuffer - unbounded queue
        var sink = Sinks.many().unicast().onBackpressureBuffer();

        //handle through which subscribers will receive item
        var flux = sink.asFlux();

        sink.tryEmitNext("hi");
        sink.tryEmitNext("how are you");
        sink.tryEmitNext("?");

        // unicast only allow a single subscriber
        flux.subscribe(Util.subscriber("sam"));
        flux.subscribe(Util.subscriber("mike"));
    }
}
