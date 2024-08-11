package com.sdragon.sec04;

import com.sdragon.common.Util;
import reactor.core.publisher.Flux;

public class Lec01FluxCreate {

    public static void main(String[] args) {
        //FluxSink
//        Flux.create(fluxSink -> {
//                    fluxSink.next(1);
//                    fluxSink.next(1);
//                    fluxSink.complete();
//                })
//                .subscribe(Util.subscriber());
        Flux.create(fluxSink -> {
                    String country;
                    do {
                        country = Util.faker().country().name();
                        fluxSink.next(country);
                    } while (!country.equalsIgnoreCase("canada"));
                    fluxSink.complete();
                })
                .subscribe(Util.subscriber());
    }
}
