package com.cafepos.domain;

import com.cafepos.payment.PaymentStrategy;
import com.cafepos.common.Money;
import com.cafepos.observer.OrderObserver;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public final class Order {
    private final long id;
    private final List<LineItem> items = new ArrayList<>();
    public Order(long id) { this.id = id; }
//    public void addItem(LineItem li) {
//        if (li.quantity() > 0) {
//            items.add(li);
//        }
//    }
    public Money subtotal() {
        return
                items.stream().map(LineItem::lineTotal).reduce(Money.zero()
                        , Money::add);
    }
    public Money taxAtPercent(int percent) {
        Money base = subtotal();
        BigDecimal rate = BigDecimal.valueOf(percent).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        BigDecimal taxPay = base.getMoney().multiply(rate);
        double tax = taxPay.doubleValue();
        return Money.of(tax);
    }
    public Money totalWithTax(int percent) {
        Money taxPay = taxAtPercent(percent);
        Money subtotal = subtotal();
        return taxPay.add(subtotal);
    }

    public long getId() {
        return id;
    }

    public List<LineItem> getItems() {
        return items;
    }

//    public void pay(PaymentStrategy strategy) {
//        if (strategy == null) {
//            throw new IllegalArgumentException("strategy required");
//        }
//        strategy.pay(this);
//    }

    // Order.java (extension) -- You must complete missing parts using following instructions
    // 1) Maintain subscriptions
    private final List<OrderObserver> observers = new ArrayList<>();
    public void register(OrderObserver o) {
// TODO: add null check and add the observer
        if (o == null) {
            throw new IllegalArgumentException("observer is required");
        }
        if (observers.contains(o)) {
            throw new IllegalArgumentException("observer already added");
        }
        observers.add(o);
    }
    public void unregister(OrderObserver o) {
// TODO: remove the observer if present
        if (o != null) {
            observers.remove(o);
        }
    }
    // 2) Publish events
    private void notifyObservers(String eventType) {
// TODO: iterate observers and call updated(this, eventType)
        for (OrderObserver observer : observers) {
            observer.updated(this, eventType);
        }
    }
    // 3) Hook notifications into existing behaviors
    //@Override
    public void addItem(LineItem li) {
// TODO: call super/add to items and then notifyObservers("itemAdded")
        if (li.quantity() > 0) {
            items.add(li);
        }
        notifyObservers("itemAdded");
    }
    //@Override
    public void pay(PaymentStrategy strategy) {
// TODO: delegate to strategy as before, then notifyObservers("paid")
        if (strategy == null) {
            throw new IllegalArgumentException("strategy required");
        }
        strategy.pay(this);
        notifyObservers("paid");
    }
    public void markReady() {
// TODO: just publish notifyObservers("ready")
        notifyObservers("ready");
    }
}