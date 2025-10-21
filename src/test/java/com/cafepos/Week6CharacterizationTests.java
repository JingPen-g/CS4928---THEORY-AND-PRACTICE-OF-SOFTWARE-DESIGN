package com.cafepos;
import com.cafepos.common.Money;
import com.cafepos.factory.ProductFactory;
import com.cafepos.payment.CardPayment;
import com.cafepos.payment.CashPayment;
import com.cafepos.pricing.*;
import com.cafepos.smells.OrderManagerGod;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class Week6CharacterizationTests {
    @Test void no_discount_cash_payment() {
        int TAX_PERCENT = 10;
        PricingService pricing = new PricingService(
                new NoDiscount(),
                new FixedRateTaxPolicy(TAX_PERCENT)
        );
        CheckoutService checkout = new CheckoutService(
                new ProductFactory(),
                pricing,
                new ReceiptPrinter(),
                new CashPayment(),
                TAX_PERCENT
        );
        String receipt = checkout.checkout("ESP+SHOT+OAT", 1);
        assertTrue(receipt.startsWith("Order (ESP+SHOT+OAT) x1"));
        assertTrue(receipt.contains("Subtotal: 3.80"));
        assertTrue(receipt.contains("Tax (10%): 0.38"));
        assertTrue(receipt.contains("Total: 4.18"));
    }
    @Test void loyalty_discount_card_payment() {
        int TAX_PERCENT = 10;
        PricingService pricing = new PricingService(
                new LoyaltyPercentDiscount(5),
                new FixedRateTaxPolicy(TAX_PERCENT)
        );
        CheckoutService checkout = new CheckoutService(
                new ProductFactory(),
                pricing,
                new ReceiptPrinter(),
                new CardPayment("1234567887654321"),
                TAX_PERCENT
        );
        String receipt = checkout.checkout("LAT+L", 2);
        assertTrue(receipt.contains("Subtotal: 7.80"));
        assertTrue(receipt.contains("Discount: -0.39"));
        assertTrue(receipt.contains("Tax (10%): 0.74"));
        assertTrue(receipt.contains("Total: 8.15"));
    }
    @Test void coupon_fixed_amount_and_qty_clamp() {
        int TAX_PERCENT = 10;
        PricingService pricing = new PricingService(
                new FixedCouponDiscount(Money.of(1.00)),
                new FixedRateTaxPolicy(TAX_PERCENT)
        );
        CheckoutService checkout = new CheckoutService(
                new ProductFactory(),
                pricing,
                new ReceiptPrinter(),
                new CardPayment("1234567887654321"),
                TAX_PERCENT
        );
        String receipt = checkout.checkout("ESP+SHOT", 0);
// qty=0 clamped to 1; Espresso+SHOT = 2.50 + 0.80 = 3.30; coupon1 => -1 => 2.30; tax=0.23; total=2.53
        assertTrue(receipt.contains("Order (ESP+SHOT) x1"));
        assertTrue(receipt.contains("Subtotal: 3.30"));
        assertTrue(receipt.contains("Discount: -1.00"));
        assertTrue(receipt.contains("Tax (10%): 0.23"));
        assertTrue(receipt.contains("Total: 2.53"));
    }
}
