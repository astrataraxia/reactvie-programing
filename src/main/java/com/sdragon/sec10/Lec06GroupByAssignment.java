package com.sdragon.sec10;

import com.sdragon.common.Util;
import com.sdragon.sec10.assignment.groupBy.OrderProcessingService;
import com.sdragon.sec10.assignment.groupBy.PurchaseOrder;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec06GroupByAssignment {

    public static void main(String[] args) {

        orderStream()
                .filter(OrderProcessingService.canProcess())
                .groupBy(PurchaseOrder::category)
                .flatMap(gf -> gf.transform(OrderProcessingService.getProcessor(gf.key())))
                .subscribe(Util.subscriber());

        Util.sleepSeconds(60);
    }

    private static Flux<PurchaseOrder> orderStream() {
        return Flux.interval(Duration.ofMillis(200))
                .map(i -> PurchaseOrder.create());
    }
}
