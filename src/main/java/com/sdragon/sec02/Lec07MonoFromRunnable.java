package com.sdragon.sec02;

import com.sdragon.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class Lec07MonoFromRunnable {

    private static final Logger log = LoggerFactory.getLogger(Lec07MonoFromRunnable.class);

/*
    Project reactor is a library, and they will have to worry about all the possible use cases they could imagine.
    You don't have to use all the features.
    Sometimes I feel like I'm useless.
    This is because it's live. In fact, I might rarely use this Runnable either.
    However, I'm just introducing that it has this feature.
*/
    public static void main(String[] args) {
        getProductName(2)
                .subscribe(Util.subscriber());
    }

    private static Mono<String> getProductName(int productId)  {
        if (productId == 1) {
            return Mono.fromSupplier(() -> Util.faker().commerce().productName());
        }
//        Mono.fromRunable() can mean more than just returning Mono.empty() results,
//        providing richer information and behavior, especially in situations where error handling,
//        logging, and clear business logic representation are critical.

//        return Mono.empty();
        return Mono.fromRunnable(() -> notifyBusiness(productId));
    }

    private static void notifyBusiness(int productId) {
        log.info("notifying business on unavailable product {}", productId);
    }
}
