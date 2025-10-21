package com.cafepos.demo;

import com.cafepos.catalog.Product;
import com.cafepos.domain.LineItem;
import com.cafepos.domain.Order;
import com.cafepos.factory.ProductFactory;
import com.cafepos.payment.CardPayment;
import com.cafepos.payment.CashPayment;
import com.cafepos.pricing.*;
import com.cafepos.smells.OrderManagerGod;

public final class Week6Demo {
    public static void main(String[] args) {
        var oldManager = new OrderManagerGod(
                new ProductFactory(),
                new LoyaltyPercentDiscount(5),
                new FixedRateTaxPolicy(10),
                new ReceiptPrinter(),
                new CashPayment()
        );
        // Old behavior
        String oldReceipt = oldManager.process("LAT+L", 2, "CARD",
                "LOYAL5", false);
        // New behavior with equivalent result
        var pricing = new PricingService(new LoyaltyPercentDiscount(5),
                new FixedRateTaxPolicy(10));
        var printer = new ReceiptPrinter();
        var checkout = new CheckoutService(new ProductFactory(), pricing, printer, new CashPayment(),10);
        String newReceipt = checkout.checkout("LAT+L", 2);
        System.out.println("Old Receipt:\n" + oldReceipt);
        System.out.println("\nNew Receipt:\n" + newReceipt);
        System.out.println("\nMatch: " + oldReceipt.equals(newReceipt));
    }
}