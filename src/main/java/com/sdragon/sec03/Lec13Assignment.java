package com.sdragon.sec03;

import com.sdragon.common.Util;
import com.sdragon.sec03.assignment.StockPriceObserver;
import com.sdragon.sec03.client.ExternalServiceClient;

public class Lec13Assignment {

    public static void main(String[] args) {
        var client = new ExternalServiceClient();
        var subscriber = new StockPriceObserver();

        client.getStock()
                .subscribe(subscriber);

        Util.sleepSeconds(25);
    }
}
