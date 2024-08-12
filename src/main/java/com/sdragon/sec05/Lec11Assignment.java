package com.sdragon.sec05;

import com.sdragon.common.Util;
import com.sdragon.sec05.assignment.ProductService;
import reactor.core.publisher.Flux;

public class Lec11Assignment {

    public static void main(String[] args) {
        ProductService client = new ProductService();

        for (int i = 1; i < 5; i++) {
            client.getProductName(i)
                    .subscribe(Util.subscriber());
        }

        Util.sleepSeconds(4);
    }
}
