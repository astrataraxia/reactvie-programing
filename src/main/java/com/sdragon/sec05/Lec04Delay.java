package com.sdragon.sec05;

import com.sdragon.common.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec04Delay {

    public static void main(String[] args) {
        // delayElements -> Producer doesn't do all the work upfront
        Flux.range(1, 10)
                .log()
                .delayElements(Duration.ofSeconds(1))
                .subscribe(Util.subscriber());

        Util.sleepSeconds(12);
    }
}
