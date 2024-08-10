package com.sdragon.sec03;

import com.sdragon.common.Util;
import com.sdragon.sec03.client.ExternalServiceClient;

public class Lec8NonBlockingStreamingMessages {

    public static void main(String[] args) {
        var client = new ExternalServiceClient();

        client.getNames()
                .subscribe(Util.subscriber("sbu1"));

        client.getStock()
                .subscribe(Util.subscriber("sub2"));

        Util.sleepSeconds(25);

    }
}
