package com.cafepos.payment;

import com.cafepos.common.Money;
import com.cafepos.domain.Order;

public class TotalPaymentAdapter implements PaymentStrategy {
    private final PaymentStrategy delegate;
    private final Money total;
    public TotalPaymentAdapter(PaymentStrategy delegate, Money total) {
        this.delegate = delegate;
        this.total = total;
    }
    @Override
    public void pay(Order order) {

    }
    public void pay() {
        System.out.println(delegate.getClass().getSimpleName() +
                " Customer paid " + total + " EUR");
    }
}
