package com.sdragon.sec02;

import com.sdragon.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.List;

public class Lec05MonoFromSupplier {

    private static final Logger log = LoggerFactory.getLogger(Lec05MonoFromSupplier.class);

    // Mono.fromSupplier: Create delay, do not call the Supplier until the value is actually needed.
    public static void main(String[] args) {
        var list = List.of(1, 2, 3);

//        Mono just, if no subscribe but sum list is already in memory. so sum() is already compute
//        but reactive programing is need lazy.
//        Mono.just(sum(list))
//                .subscribe(Util.subscriber());

        //if you want to delay the compute intensive operation then we can use the fromSupplier
        Mono.fromSupplier(() -> sum(list))
                .subscribe(Util.subscriber());

    }

    private static int sum(List<Integer> list) {
        log.info("finding the sum of {}", list);
        return list.stream().mapToInt(x -> x).sum();
    }
}
