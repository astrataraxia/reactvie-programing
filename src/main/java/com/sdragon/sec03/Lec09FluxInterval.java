package com.sdragon.sec03;

import com.sdragon.common.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec09FluxInterval {

    // Interval continues to send requests at regular intervals.
    // In fact, if you don't issue a command to stop the interval,
    // logically the interval actually continues even if the main thread ends.
    public static void main(String[] args) {
        Flux.interval(Duration.ofMillis(500))
                .map(i -> Util.faker().name().firstName())
                .subscribe(Util.subscriber());

        Util.sleepSeconds(5);
    }
}
