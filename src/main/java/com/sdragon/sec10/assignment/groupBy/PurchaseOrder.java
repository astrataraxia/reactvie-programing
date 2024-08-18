package com.sdragon.sec10.assignment.groupBy;

import com.sdragon.common.Util;

public record PurchaseOrder(String item, String category, Integer price) {

    public static PurchaseOrder create() {
        var commerce = Util.faker().commerce();
        return new PurchaseOrder(
                commerce.productName(),
                commerce.department(),
                Util.faker().random().nextInt(10,  100)
        );
    }
}
