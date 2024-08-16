package com.sdragon.sec09;

import com.sdragon.common.Util;
import com.sdragon.sec09.assignment.client.ExternalServiceClient;

public class Lec08Assignment {

    public static void main(String[] args) {

        var client = new ExternalServiceClient();

        for (int i = 1; i <=10; i++) {
            client.getProduct(i)
                    .subscribe(Util.subscriber());
        }

        Util.sleepSeconds(2);
    }
}
