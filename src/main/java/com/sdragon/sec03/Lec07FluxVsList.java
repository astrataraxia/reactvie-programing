package com.sdragon.sec03;

import com.sdragon.common.Util;
import com.sdragon.sec01.subscriber.SubscriberImpl;
import com.sdragon.sec03.helper.NameGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec07FluxVsList {

    private static final Logger log = LoggerFactory.getLogger(Lec07FluxVsList.class);

    public static void main(String[] args) {

        // all or nothing only 10 or 0
//        var list = NameGenerator.getNamesList(10);
//        log.info(list.toString());


        // It is stop if you want not need 10, if you need 3, just received 3 and cancel
        var subscriber = new SubscriberImpl();
        NameGenerator.getNamesFlux(10)
                .subscribe(subscriber);

        subscriber.getSubscription().request(3);
        subscriber.getSubscription().cancel();
    }
}
