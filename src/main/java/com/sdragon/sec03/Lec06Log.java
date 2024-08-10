package com.sdragon.sec03;

import com.sdragon.common.Util;
import reactor.core.publisher.Flux;

public class Lec06Log {

    public static void main(String[] args) {
        // log is processor to help debug
        //.log() is a subscription to another publisher depending on where you put it.
        // and you can name it easy to recognize
        Flux.range(1, 5)
                .log("range-map")
                .map(i -> Util.faker().name().firstName())
                .log("map-subscribe")
                .subscribe(Util.subscriber());
    }
}
