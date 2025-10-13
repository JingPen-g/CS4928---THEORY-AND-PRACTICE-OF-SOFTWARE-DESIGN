package com.cafepos.domain;

import com.cafepos.domain.Order;
import com.cafepos.observer.OrderObserver;

public interface OrderPublisher {
    void register(OrderObserver o);
    void unregister(OrderObserver o);
    void notifyObservers(Order order, String eventType);
}
