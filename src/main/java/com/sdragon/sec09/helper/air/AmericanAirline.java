package com.sdragon.sec09.helper.air;

import com.sdragon.common.Util;
import com.sdragon.sec09.helper.Flight;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class AmericanAirline {

    private static final String AIRLINE = "American Airline";

    public static Flux<Flight> getFlights() {
        return Flux.range(1, Util.faker().random().nextInt(3, 5))
                .delayElements(Duration.ofMillis(Util.faker().random().nextInt(300, 800)))
                .map(i -> new Flight(AIRLINE, Util.faker().random().nextInt(400, 900)))
                .transform(Util.fluxLogger(AIRLINE));
    }
}
