package com.sdragon.sec02;

import com.sdragon.common.Util;
import com.sdragon.sec02.client.ExternalServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

/*
    To demo non-blocking IO
    Ensure that the external service is up and running!
 */
public class Lec11NonBlockingIO {

    private static final Logger log = LoggerFactory.getLogger(Lec11NonBlockingIO.class);

    public static void main(String[] args) {
        var client = new ExternalServiceClient();

        log.info("starting");

        // server responses is 1s. If traditional programing, It will take 100 seconds to complete the for loop.
        // Request product id 1, get a response and move on to the next iteration...
        // the remaining time is constantly blocked in the loop. So I need 100 seconds or 100 threads.
        // but reactive programing, All requests are sent at the same time, and one thread is enough
        for (int i = 1; i <= 100; i++) {
            client.getProductName(i)
                    .subscribe(Util.subscriber());
        }
        Util.sleepSeconds(2);
    }
//     do not use block statement. If you use block statement it is not non-blocking, change blocking.
//     so you do not use that in reactive programing paradigm
}
