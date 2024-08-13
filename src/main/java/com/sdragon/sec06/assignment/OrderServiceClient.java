package com.sdragon.sec06.assignment;

import com.sdragon.common.AbstractHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.Objects;

/*
 * Order Stream Message format - item:category:price:quantity
 * Original inventory - assume we have 500 quantities for each category.
 *                      so deduct quantity for every order based on the category
 */
public class OrderServiceClient extends AbstractHttpClient {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceClient.class);
    private Flux<Order> orderFlux;

    public Flux<Order> orderStream() {
        if (Objects.isNull(orderFlux)) {
            this.orderFlux = getOrderStream();
        }
        return this.orderFlux;
    }

    private Flux<Order> getOrderStream() {
        return this.httpClient.get()
                .uri("/demo04/orders/stream")
                .responseContent()
                .asString()
                .map(this::parse)
                .doOnNext(o -> log.info("{}", o))
                .publish()
                .refCount(2);
    }

    private Order parse(String message) {
        var arr = message.split(":");
        return new Order(
                arr[0],
                arr[1],
                Integer.parseInt(arr[2]),
                Integer.parseInt(arr[3])
        );
    }
}
