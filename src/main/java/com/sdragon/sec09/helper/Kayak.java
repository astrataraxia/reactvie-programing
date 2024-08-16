package com.sdragon.sec09.helper;

import com.sdragon.sec09.helper.air.AmericanAirline;
import com.sdragon.sec09.helper.air.Emirates;
import com.sdragon.sec09.helper.air.KoreanAirline;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Kayak {


    public static Flux<Flight> getFlights() {
        return Flux.merge(
                        AmericanAirline.getFlights(),
                        Emirates.getFlights(),
                        KoreanAirline.getFlights()
                )
                .take(Duration.ofSeconds(2));
    }
}
