package com.sdragon.sec04;

import com.sdragon.common.Util;
import reactor.core.publisher.Flux;

public class Lec07FluxGenerateUntil {

    public static void main(String[] args) {
        ifGenerate();
//        takeUntilGenerate();
    }

    private static void takeUntilGenerate() {
        Flux.<String>generate(synchronousSink -> {
                    synchronousSink.next(Util.faker().country().name());
                }).takeUntil(c -> c.equalsIgnoreCase("canada"))
                .subscribe(Util.subscriber());
    }

    private static void ifGenerate() {
        Flux.generate(synchronousSink -> {
                    var country = Util.faker().country().name();
                    synchronousSink.next(country);
                    if (country.equalsIgnoreCase("canada")) {
                        synchronousSink.complete();
                    }
                })
                .subscribe(Util.subscriber());
    }
}
