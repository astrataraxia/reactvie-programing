package com.sdragon.sec04;

import com.sdragon.common.Util;
import reactor.core.publisher.Flux;

public class Lec08GenerateWithState {

    public static void main(String[] args) {
        Flux.generate(
                () -> 0,
                (count, sink) -> {
                    var country = Util.faker().country().name();
                    sink.next(country);
                    count++;
                    if (count == 10 || country.equalsIgnoreCase("canada")) {
                        sink.complete();
                    }
                    return count;
                }).subscribe(Util.subscriber());

    }

}
