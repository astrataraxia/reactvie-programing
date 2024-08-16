package com.sdragon.sec09;

import com.sdragon.common.Util;
import com.sdragon.sec09.applications.OrderService;
import com.sdragon.sec09.applications.User;
import com.sdragon.sec09.applications.UserService;

public class Lec11FluxFlatMap {

    public static void main(String[] args) {
        /*
         * Get all the orders from order service!
         * It is similarly work mereWith, so many subscriber emit data
         * If you want size limit .flatMap(OrderService::getUserOrders, size)
         * you can typed size
         */

        UserService.getAllUsers()
                .map(User::id)
                .flatMap(OrderService::getUserOrders)
                .subscribe(Util.subscriber());

        Util.sleepSeconds(2);
    }
}
