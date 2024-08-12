package com.sdragon.sec05;

import com.sdragon.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Lec07DefaultIfEmpty {

    public static void main(String[] args) {
//        Mono.empty()
//                .defaultIfEmpty("fallback")
//                .subscribe(Util.subscriber());

        Flux.range(1, 10)
                .filter(i -> i > 11)
                .defaultIfEmpty(50)
                .subscribe(Util.subscriber());

    }
}
