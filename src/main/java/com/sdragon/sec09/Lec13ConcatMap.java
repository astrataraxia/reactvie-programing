package com.sdragon.sec09;

import com.sdragon.common.Util;
import com.sdragon.sec09.assignment.client.ExternalServiceClient;
import reactor.core.publisher.Flux;

public class Lec13ConcatMap {

    public static void main(String[] args) {

        var client = new ExternalServiceClient();

        Flux.range(1, 10)
                .concatMap(ids -> client.getProduct(ids))
                .subscribe(Util.subscriber());

        Util.sleepSeconds(20);
    }
}
