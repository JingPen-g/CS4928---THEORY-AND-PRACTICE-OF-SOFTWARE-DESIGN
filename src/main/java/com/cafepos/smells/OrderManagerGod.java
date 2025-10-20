package com.cafepos.smells;

import com.cafepos.common.Money;
import com.cafepos.factory.ProductFactory;
import com.cafepos.catalog.Product;
public class OrderManagerGod { // God Class
    public static int TAX_PERCENT = 10; // Global/Static State: risky and hard to test. Primitive Obsession: `TAX_PERCENT` as primitive; magic numbers for rates. Shotgun Surgery: Tax rules embedded inline; any change requires editing this method.
    public static String LAST_DISCOUNT_CODE = null; // Global/Static State: risky and hard to test.
    public static String process(String recipe, int qty, String paymentType, String discountCode, boolean printReceipt) { //Long Method: One method performs creation, pricing, discounting, tax, payment I/O, and printing.
        ProductFactory factory = new ProductFactory();
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
        Money discount = Money.zero();
        if (discountCode != null) {
            if (discountCode.equalsIgnoreCase("LOYAL5")) { // Primitive Obsession: `discountCode` strings. Shotgun Surgery: discount rules embedded inline; any change requires editing this method.
                discount = Money.of(subtotal.asBigDecimal() // Duplicated Logic: Money and BigDecimal manipulations scattered inline.
                        .multiply(java.math.BigDecimal.valueOf(5))
                        .divide(java.math.BigDecimal.valueOf(100)));
            } else if (discountCode.equalsIgnoreCase("COUPON1")) { // Primitive Obsession: `discountCode` strings. Shotgun Surgery: discount rules embedded inline; any change requires editing this method.
                discount = Money.of(1.00);
            } else if (discountCode.equalsIgnoreCase("NONE")) { // Primitive Obsession: `discountCode` strings. Shotgun Surgery: discount rules embedded inline; any change requires editing this method.
                discount = Money.zero();
            } else {
                discount = Money.zero();
            }
            LAST_DISCOUNT_CODE = discountCode;
        }
        Money discounted = Money.of(subtotal.asBigDecimal().subtract(discount.asBigDecimal())); // Duplicated Logic: Money and BigDecimal manipulations scattered inline.
        if (discounted.asBigDecimal().signum() < 0) discounted = Money.zero();
        var tax = Money.of(discounted.asBigDecimal() // Duplicated Logic: Money and BigDecimal manipulations scattered inline.
                .multiply(java.math.BigDecimal.valueOf(TAX_PERCENT))
                .divide(java.math.BigDecimal.valueOf(100)));
        var total = discounted.add(tax);
        if (paymentType != null) { // Shotgun Surgery: discount rules embedded inline; any change requires editing this method.
            if (paymentType.equalsIgnoreCase("CASH")) { // Primitive Obsession: `paymentType` Strings
                System.out.println("[Cash] Customer paid " + total + " EUR");
            } else if (paymentType.equalsIgnoreCase("CARD")) { // Primitive Obsession: `paymentType` Strings
                System.out.println("[Card] Customer paid " + total + " EUR with card ****1234");
            } else if (paymentType.equalsIgnoreCase("WALLET")) { // Primitive Obsession: `paymentType` Strings
                System.out.println("[Wallet] Customer paid " + total + " EUR via wallet user-wallet-789");
            } else {
                System.out.println("[UnknownPayment] " + total);
            }
        }
        StringBuilder receipt = new StringBuilder();
        receipt.append("Order (").append(recipe).append(") x").append(qty).append("\n");
                receipt.append("Subtotal: ").append(subtotal).append("\n");
        if (discount.asBigDecimal().signum() > 0) {
            receipt.append("Discount: -").append(discount).append("\n");
        }
        receipt.append("Tax (").append(TAX_PERCENT).append("%): ").append(tax).append("\n");
        receipt.append("Total: ").append(total);
        String out = receipt.toString();
        if (printReceipt) {
            System.out.println(out);
        }
        return out;
    }
}
