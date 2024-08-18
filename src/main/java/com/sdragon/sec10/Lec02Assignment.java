package com.sdragon.sec10;

import com.sdragon.common.Util;
import com.sdragon.sec10.assignment.buffer.BookOrder;
import com.sdragon.sec10.assignment.buffer.RevenueReport;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Lec02Assignment {

    public static void main(String[] args) {
        var allowedCategories = Set.of(
                "Science fiction",
                "Fantasy",
                "Suspense/Thriller"
        );

        orderStream()
                .filter(o -> allowedCategories.contains(o.genre()))
                .buffer(Duration.ofSeconds(5))
                .map(o -> generateReport(o))
                .subscribe(Util.subscriber());

        Util.sleepSeconds(60);
    }

    private static Flux<BookOrder> orderStream() {
        return Flux.interval(Duration.ofMillis(200))
                .map(i -> BookOrder.create());
    }

    private static RevenueReport generateReport(List<BookOrder> orders) {
        var revenue = orders.stream()
                .collect(Collectors.groupingBy(
                        BookOrder::genre,
                        Collectors.summingInt(BookOrder::price)
                ));
        return new RevenueReport(LocalTime.now(), revenue);
    }
}
