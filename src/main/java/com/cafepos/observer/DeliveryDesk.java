package com.cafepos.observer;

import com.cafepos.domain.Order;
import com.cafepos.observer.OrderObserver;

public final class DeliveryDesk implements OrderObserver {
    @Override
    public void updated(Order order, String eventType) {
// TODO: on "ready" -> print "[Delivery] Order #<id>is ready for delivery"
        if (eventType.equals("ready")) {
            System.out.println("[Delivery] Order#" + order.getId() +": is ready for delivery");
        }
    }
}