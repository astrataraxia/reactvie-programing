package com.sdragon.sec02;

import com.sdragon.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.List;

public class Lec10MonoDefer {

    private static final Logger log = LoggerFactory.getLogger(Lec10MonoDefer.class);

//    If defer is used, publisher creation itself can be made after subscribers are created.
//    Publisher creation itself is said not to take long, but if it not, you don't have to create it in advance.
//    you can lazy create publisher
    public static void main(String[] args) {
        Mono.defer(() -> createPublisher())
                .subscribe(Util.subscriber());
    }

    private static Mono<Integer> createPublisher() {
        log.info("Creating publisher");
        var list = List.of(1, 2, 3);
        Util.sleepSeconds(1);
        return Mono.fromSupplier(() -> sum(list));
    }

    //time-consuming business logic
    private static int sum(List<Integer> list) {
        log.info("finding the sum of {}", list);
        Util.sleepSeconds(3);
        return list.stream().mapToInt(a -> a).sum();
    }
}
