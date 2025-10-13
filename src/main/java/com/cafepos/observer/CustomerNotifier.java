package com.cafepos.observer;

import com.cafepos.domain.Order;
import com.cafepos.observer.OrderObserver;

public final class CustomerNotifier implements OrderObserver {
    @Override
    public void updated(Order order, String eventType) {
// TODO: print "[Customer] Dear customer, your Order#<id> has been updated: <event>."
        System.out.println("[Customer] Dear customer, your Order#" + order.getId() +": has been updated: " + eventType);
    }

}