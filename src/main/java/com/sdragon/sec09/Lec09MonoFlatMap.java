package com.sdragon.sec09;

import com.sdragon.common.Util;
import com.sdragon.sec09.applications.PaymentService;
import com.sdragon.sec09.applications.UserService;

/*
 * Sequential non-blocking IO calls!
 * flatMap if used to flatten the inner publisher / to subscribe to the inner publisher
 */
public class Lec09MonoFlatMap {


    public static void main(String[] args) {
        /*
         * We have username
         * Get user account balance
         * Map is appropriate inMemory computation
         */

        UserService.getUserId("sam")
                .flatMap(PaymentService::getUserBalance)
                .subscribe(Util.subscriber());
    }
}
