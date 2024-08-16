package com.sdragon.sec09.helper.air;

import com.sdragon.common.Util;
import com.sdragon.sec09.helper.Flight;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class KoreanAirline {

    private static final String AIRLINE = "Korean Airline";

    public static Flux<Flight> getFlights() {
        return Flux.range(1, Util.faker().random().nextInt(5, 10))
                .delayElements(Duration.ofMillis(Util.faker().random().nextInt(200, 1200)))
                .map(i -> new Flight(AIRLINE, Util.faker().random().nextInt(300, 1200)))
                .transform(Util.fluxLogger(AIRLINE));
    }
}
