package com.sdragon.sec04;

import com.sdragon.common.Util;
import reactor.core.publisher.Flux;

import java.util.stream.IntStream;

public class Lec05TakeOperator {

    public static void main(String[] args) {
        takeUntil();
    }

    // Take operator is like java stream limit
    private static void take() {
        Flux.range(1, 10)
                .log("take")
                .take(30)
                .log("sub")
                .subscribe(Util.subscriber());
    }
    private static void takeWhile() {
        Flux.range(1, 10)
                .log("take")
                .takeWhile(i -> i<5) // stop when the condition is not met
                .log("sub")
                .subscribe(Util.subscriber());
    }
    private static void takeUntil() {
        Flux.range(1, 10)
                .log("take")
                .takeUntil(i -> i<5) // stop when the condition is met + allow the last item
                .log("sub")
                .subscribe(Util.subscriber());
    }
}
