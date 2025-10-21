package com.cafepos.smells;

import com.cafepos.common.Money;
import com.cafepos.domain.Order;
import com.cafepos.factory.ProductFactory;
import com.cafepos.catalog.Product;
import com.cafepos.payment.PaymentStrategy;
import com.cafepos.pricing.*;

public class OrderManagerGod { // God Class
    private final ProductFactory factory;
    private final DiscountPolicy discountPolicy;
    private final TaxPolicy taxPolicy;
    private final ReceiptPrinter printer;
    private final PaymentStrategy paymentStrategy;
    public OrderManagerGod(ProductFactory factory,
                           DiscountPolicy discountPolicy,
                           TaxPolicy taxPolicy,
                           ReceiptPrinter printer,
                           PaymentStrategy paymentStrategy) {
        this.factory = factory;
        this.discountPolicy = discountPolicy;
        this.taxPolicy = taxPolicy;
        this.printer = printer;
        this.paymentStrategy = paymentStrategy;
    }
    public String process(String recipe, int qty, String paymentType, String discountCode, boolean printReceipt) { //Long Method: One method performs creation, pricing, discounting, tax, payment I/O, and printing.
        Product product = factory.create(recipe);
        Money unitPrice;
        try {
            var priced = product instanceof com.cafepos.decorator.Priced p ? p.price() : product.basePrice();
            unitPrice = priced;
        } catch (Exception e) {
            unitPrice = product.basePrice();
        }
        if (qty <= 0) qty = 1;
        Money subtotal = unitPrice.multiply(qty);
        Money discount = discountPolicy.discountOf(subtotal);
        Money discounted = Money.of(subtotal.asBigDecimal().subtract(discount.asBigDecimal())); // Duplicated Logic: Money and BigDecimal manipulations scattered inline.
        if (discounted.asBigDecimal().signum() < 0) discounted = Money.zero();
        Money tax = taxPolicy.taxOn(discounted);
        Money total = discounted.add(tax);
        Order order = new Order(1L);
        order.pay(paymentStrategy);
        PricingService pricingService = new PricingService(discountPolicy, taxPolicy);
        PricingService.PricingResult pr = pricingService.price(subtotal);
        int taxPercent = (taxPolicy instanceof FixedRateTaxPolicy fixed) ? fixed.getPercent() : 0;
        String out = printer.format(recipe, qty, pr, taxPercent);
        if (printReceipt) {
            System.out.println(out);
        }
        return out;
    }
}
