package com.sdragon.sec03.assignment;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 *  Create a subscriber with 1000 balance.
 *  Whenever the price drop below 90, you buy a stock
 *  when the price goes above 110
 *  - you sell all the quantities.
 *  - cancel the subscription.
 *  - print the profit you made!
 */

public class StockPriceObserver implements Subscriber<Integer> {

    private static final Logger log = LoggerFactory.getLogger(StockPriceObserver.class);

    private int quantity = 0;
    private int balance = 1000;
    private Subscription subscription;

    @Override
    public void onSubscribe(Subscription subscription) {
        subscription.request(Long.MAX_VALUE);
        this.subscription = subscription;
    }

    @Override
    public void onNext(Integer price) {
        if (price <= 90 && balance >= price) {
            quantity++;
            balance -= price;
            log.info("bout a stock at {}. total quantity: {}, remaining balance: {}", price, quantity, balance);
        } else if (price <= 90){
            log.info("Do not have enough money. Cannot buy stock");
        } else if (price >= 110 && quantity > 0) {
            log.info("selling {} quantities at {}", quantity, price);
            balance = balance + (quantity * price);
            quantity = 0;
            subscription.cancel();
            log.info("profile: {}", (balance - 1000));
        }
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("error", throwable);
    }

    @Override
    public void onComplete() {
        log.info("completed");
    }
}
