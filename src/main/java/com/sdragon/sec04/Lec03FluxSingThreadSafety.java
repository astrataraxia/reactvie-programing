package com.sdragon.sec04;

import com.sdragon.common.Util;
import com.sdragon.sec04.helper.NameGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.ArrayList;

public class Lec03FluxSingThreadSafety {

    private static final Logger log = LoggerFactory.getLogger(Lec03FluxSingThreadSafety.class);

    public static void main(String[] args) {
        // arrayList is not thread safe
//        notThreadSafe();
        // flux sink is thread safe. we get all the 10000 items safely into array list
        // multiple thread item comes generator but flux sink receive one by one to subscriber
        /*
        * 1 Internal synchronization mechanism
        * 2 Single Producer Principles
        * 3 Non-blocking backpressor
        * 4 Ensuring the order of events
        * 5 Atomic operation
        * 6 Internal buffering
        */
        threadSafe();
    }

    private static void notThreadSafe(){
        var list = new ArrayList<Integer>();

        Runnable runnable = () -> {
            for (int i = 0; i < 1000; i++) {
                list.add(i);
            }
        };

        for (int i = 0; i < 10; i++) {
            Thread.ofPlatform().start(runnable);
        }
        Util.sleepSeconds(3);
        log.info("list size: {}", list.size());
    }

    private static void threadSafe() {
        var list = new ArrayList<String>();
        var generator = new NameGenerator();

        Flux.create(generator)
                .subscribe(list::add);

        Runnable runnable = () -> {
            for (int i = 0; i < 1000; i++) {
                generator.generate();
            }
        };

        for (int i = 0; i < 10; i++) {
            Thread.ofPlatform().start(runnable);
        }
        Util.sleepSeconds(3);
        log.info("list size: {}", list.size());
    }
}
