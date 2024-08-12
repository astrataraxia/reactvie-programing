package com.sdragon.sec05;

import com.sdragon.common.Util;
import reactor.core.publisher.Flux;

public class Lec02HandleUntilAssignment {

    public static void main(String[] args) {
        handleUntilAssignment();
    }

    private static void handleUntilAssignment() {
        Flux.<String>generate(sink ->
                        sink.next(Util.faker().country().name())
                ).handle(((country, sink) -> {
                    sink.next(country);
                    if (country.equalsIgnoreCase("japan")) {
                        sink.complete();
                    }
                }))
                .subscribe(Util.subscriber());
    }
}
