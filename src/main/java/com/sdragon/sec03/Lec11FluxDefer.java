package com.sdragon.sec03;

import com.sdragon.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

public class Lec11FluxDefer {

    private static final Logger log = LoggerFactory.getLogger(Lec11FluxDefer.class);

    public static void main(String[] args) {
         Flux.fromIterable(createList("eager"));

        // Lazy approach using defer
        Flux.defer(() -> Flux.fromIterable(createList("lazy")))
                .subscribe(Util.subscriber());

        log.info("Fluxes created");
        Util.sleepSeconds(2);
    }

    private static List<Integer> createList(String name) {
        log.info("Creating list: {}", name);
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            list.add(i);
        }
        return list;
    }
}
