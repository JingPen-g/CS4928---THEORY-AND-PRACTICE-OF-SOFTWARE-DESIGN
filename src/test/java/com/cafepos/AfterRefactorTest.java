package com.cafepos;

import com.cafepos.common.Money;
import com.cafepos.pricing.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AfterRefactorTest {
    @Test
    void loyalty_discount_5_percent() {
        DiscountPolicy d = new LoyaltyPercentDiscount(5);
        assertEquals(Money.of(0.39), d.discountOf(Money.of(7.80)));
    }

    @Test
    void fixed_coupon_discount_capped_at_subtotal() {
        DiscountPolicy d = new FixedCouponDiscount(Money.of(10.00));
        assertEquals(Money.of(7.80), d.discountOf(Money.of(7.80)));
    }

    @Test
    void loyalty_discount_zero_percent() {
        DiscountPolicy d = new LoyaltyPercentDiscount(0);
        assertEquals(Money.zero(), d.discountOf(Money.of(10.00)));
    }

    @Test void fixed_rate_tax_10_percent() {
        TaxPolicy t = new FixedRateTaxPolicy(10);
        assertEquals(Money.of(0.74), t.taxOn(Money.of(7.41)));
    }

    @Test void fixed_rate_tax_zero_percent() {
        TaxPolicy t = new FixedRateTaxPolicy(0);
        assertEquals(Money.zero(), t.taxOn(Money.of(100.00)));
    }

    @Test void pricing_pipeline() {
        var pricing = new PricingService(new LoyaltyPercentDiscount(5), new
                FixedRateTaxPolicy(10));
        var pr = pricing.price(Money.of(7.80));
        assertEquals(Money.of(0.39), pr.discount());
        assertEquals(Money.of(7.41),
                Money.of(pr.subtotal().asBigDecimal().subtract(pr.discount().asBigDecimal
                        ())));
        assertEquals(Money.of(0.74), pr.tax());
        assertEquals(Money.of(8.15), pr.total());
    }

}
