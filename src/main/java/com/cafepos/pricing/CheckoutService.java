package com.cafepos.pricing;

import com.cafepos.catalog.Product;
import com.cafepos.common.Money;
import com.cafepos.domain.LineItem;
import com.cafepos.domain.Order;
import com.cafepos.factory.ProductFactory;
import com.cafepos.payment.PaymentStrategy;
import com.cafepos.payment.TotalPaymentAdapter;

public class CheckoutService {
    private final ProductFactory factory;
    private final PricingService pricing;
    private final ReceiptPrinter printer;
    private final PaymentStrategy payment;
    private final int taxPercent;
    public CheckoutService(ProductFactory factory, PricingService
            pricing, ReceiptPrinter printer, PaymentStrategy payment, int taxPercent) {
        this.factory = factory;
        this.pricing = pricing;
        this.printer = printer;
        this.payment = payment;
        this.taxPercent = taxPercent;
    }
    public String checkout(String recipe, int qty) {
        Product product = factory.create(recipe);
        if (qty <= 0) qty = 1;
        Money unit = (product instanceof com.cafepos.decorator.Priced p)
                ? p.price() : product.basePrice();
        Money subtotal = unit.multiply(qty);
        var result = pricing.price(subtotal);
        TotalPaymentAdapter adapter = new TotalPaymentAdapter(payment, result.total());
        adapter.pay();
        return printer.format(recipe, qty, result, taxPercent);
    }
}
