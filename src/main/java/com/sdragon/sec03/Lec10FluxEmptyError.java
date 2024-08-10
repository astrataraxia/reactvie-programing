package com.sdragon.sec03;

import com.sdragon.common.Util;
import reactor.core.publisher.Flux;

public class Lec10FluxEmptyError {
    // Flux doesn't always have to send Item. This is definitely the same mechanism as Mono.
    public static void main(String[] args) {
        Flux.empty()
                .subscribe(Util.subscriber());

        Flux.error(new RuntimeException("opps"))
                .subscribe(Util.subscriber());
    }
}
