package com.sdragon.sec03;

import com.sdragon.common.Util;
import reactor.core.publisher.Flux;

import java.util.List;

public class Lec04FluxFromStream {

    /*
     *  List, Array, even java stream can change Flux
     */

    public static void main(String[] args) {
        var list = List.of(1, 2, 3, 4);
        var stream = list.stream();

/*
       if you stream change flux, subscriber more then 1, error.
       because it is not because of flux. It's about java's stream.
       Java stream basically cannot do the following once consumed.
 */

        // Flux.fromStream(stream)  // stream consumed
        // so if you subscribe many subscriber, make streamSupplier
        Flux<Integer> flux = Flux.fromStream(list::stream);
        flux.subscribe(Util.subscriber("sub1"));
        flux.subscribe(Util.subscriber("sub2"));
    }
}
