package com.cafepos.observer;

import com.cafepos.domain.Order;


public final class KitchenDisplay implements OrderObserver {
    @Override
    public void updated(Order order, String eventType) {
// TODO: on "itemAdded" -> print "[Kitchen] Order#<id>: item added"
// on "paid" -> print "[Kitchen] Order#<id>: payment received"
        if (eventType.equals("itemAdded")) {
            System.out.println("[Kitchen] Order#" + order.getId() +": item added");
        }
        if (eventType.equals("paid")) {
            System.out.println("[Kitchen] Order#" + order.getId() +": payment received");
        }
    }
}