package com.sdragon.sec05;

import com.sdragon.common.Util;
import reactor.core.publisher.Flux;

public class Lec08SwitchIfEmpty {

    public static void main(String[] args) {

        /*
         *  How to use it? switchIfEmpty
         *  postgres + redis
         *  if redis empty, check postgresQL
         */
        Flux.range(1, 10)
                .filter(i -> i > 11)
                .switchIfEmpty(fallback())
                .subscribe(Util.subscriber());
    }

    private static Flux<Integer> fallback() {
        return Flux.range(100, 3);
    }
}
