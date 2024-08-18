package com.sdragon.sec10;

import com.sdragon.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class Lec05GroupByFlux {

    private static final Logger log = LoggerFactory.getLogger(Lec05GroupByFlux.class);

    public static void main(String[] args) {
        Flux.range(1, 5)
                .delayElements(Duration.ofSeconds(1))
                .map(i -> i * 2)
                .startWith(1)
                .groupBy(i -> i % 2)
                .flatMap(groupedFlux -> processEvent(groupedFlux))
                .subscribe();

        Util.sleepSeconds(60);
    }

    private static Mono<Void> processEvent(GroupedFlux<Integer, Integer> groupedFlux) {
        log.info("received flux for {}", groupedFlux.key());
        return groupedFlux
                .doOnNext(i -> log.info("key: {}, item: {}", groupedFlux.key(), i))
                .doOnComplete(() -> log.info("{} completed", groupedFlux.key()))
                .then();
    }
}
