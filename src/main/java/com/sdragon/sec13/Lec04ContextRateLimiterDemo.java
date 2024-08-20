package com.sdragon.sec13;

import com.sdragon.common.Util;
import com.sdragon.sec13.client.ExternalServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.util.context.Context;

public class Lec04ContextRateLimiterDemo {

    private static final Logger log = LoggerFactory.getLogger(Lec04ContextRateLimiterDemo.class);

    public static void main(String[] args) {
        var client = new ExternalServiceClient();

        for (int i = 0; i < 20; i++) {
            client.getBook()
                    .contextWrite(Context.of("user", "sam"))
                    .subscribe(Util.subscriber());

            Util.sleepSeconds(1);
        }

        Util.sleepSeconds(5);
    }
}
