package com.sdragon.sec06.assignment;

/*
 * Order Stream Message format - item:category:price:quantity
 * Original inventory - assume we have 500 quantities for each category.
 *                      so deduct quantity for every order based on the category
 */

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class InventoryService implements OrderProcessor{


    private final Map<String, Integer> db = new HashMap<>();

    @Override
    public void consume(Order order) {
        Integer currentInventory = db.getOrDefault(order.category(), 500);
        int updateInventory = currentInventory - order.quantity();
        db.put(order.category(), updateInventory);
    }

    @Override
    public Flux<String> stream() {
        return Flux.interval(Duration.ofSeconds(2))
                .map(i -> this.db.toString());
    }
}
