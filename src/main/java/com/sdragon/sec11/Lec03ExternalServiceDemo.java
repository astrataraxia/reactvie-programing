package com.sdragon.sec11;

import com.sdragon.common.Util;
import com.sdragon.sec11.client.ClientError;
import com.sdragon.sec11.client.ExternalServiceClient;
import com.sdragon.sec11.client.ServerError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.util.retry.Retry;

import java.time.Duration;

public class Lec03ExternalServiceDemo {

    private static final Logger log = LoggerFactory.getLogger(Lec03ExternalServiceDemo.class);


    public static void main(String[] args) {
//        repeat();
        retry();

        Util.sleepSeconds(60);
    }

    private static void repeat() {
        var client = new ExternalServiceClient();
        client.getCountry()
                .repeat()
                .takeUntil(c -> c.equalsIgnoreCase("canada"))
                .subscribe(Util.subscriber());
    }
    private static void retry() {
        var client = new ExternalServiceClient();
        client.getProduct(1)  // 1 -> bad request(not retrying), 2 -> server error
                .retryWhen(retryOnServerError())
                .subscribe(Util.subscriber());
    }

    private static Retry retryOnServerError() {
        return Retry.fixedDelay(20, Duration.ofSeconds(1))
                .filter(ex -> ServerError.class.equals(ex.getClass()))
                .doBeforeRetry(rs -> log.info("retrying {}", rs.failure().getMessage()));
    }
}
