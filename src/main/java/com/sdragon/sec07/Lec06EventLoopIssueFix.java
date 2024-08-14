package com.sdragon.sec07;

import com.sdragon.common.Util;
import com.sdragon.sec07.client.ExternalServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.scheduler.Schedulers;

public class Lec06EventLoopIssueFix {

    private static final Logger log = LoggerFactory.getLogger(Lec06EventLoopIssueFix.class);


    public static void main(String[] args) {
        // A task that takes time to proceed with the process in the map has been created.
        // At this time, if you work in parallel using a scheduler, the situation that takes all of the requests will disappear.


        var client = new ExternalServiceClient();

        for (int i = 1; i <= 5; i++) {
            client.getProductName(i)
                    .publishOn(Schedulers.boundedElastic())
                    .map(m -> process(m))
                    .subscribe(Util.subscriber());
        }
        Util.sleepSeconds(20);
    }

    private static String process(String input) {
        Util.sleepSeconds(1);
        return input + "-processed";
    }
}
