package com.sdragon.sec06;

import com.sdragon.common.Util;
import com.sdragon.sec06.assignment.InventoryService;
import com.sdragon.sec06.assignment.OrderServiceClient;
import com.sdragon.sec06.assignment.RevenueService;

public class Lec06Assignment {

    public static void main(String[] args) {
        OrderServiceClient client = new OrderServiceClient();
        InventoryService inventoryService = new InventoryService();
        RevenueService revenueService = new RevenueService();

        client.orderStream().subscribe(inventoryService::consume);
        client.orderStream().subscribe(revenueService::consume);

        inventoryService.stream()
                .subscribe(Util.subscriber("inventory"));

        revenueService.stream()
                .subscribe(Util.subscriber("revenue"));

        Util.sleepSeconds(30);
    }
}
