package com.sdragon.sec09;

import com.sdragon.common.Util;
import com.sdragon.sec09.helper.Kayak;

public class Lec06MergeUseCase {

    public static void main(String[] args) {
        Kayak.getFlights()
                .subscribe(Util.subscriber());

        Util.sleepSeconds(3);
    }
}
