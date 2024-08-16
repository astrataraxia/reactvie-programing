package com.sdragon.sec09;

import com.sdragon.common.Util;
import com.sdragon.sec09.assignment.client.ExternalServiceClient;
import reactor.core.publisher.Flux;

public class Lec14CollectList {

    public static void main(String[] args) {

        var client = new ExternalServiceClient();


        Flux.range(1, 10)
                .flatMap(id -> client.getProduct(id))
                .collectList()
                .subscribe(Util.subscriber());

        Util.sleepSeconds(2);
    }
}
