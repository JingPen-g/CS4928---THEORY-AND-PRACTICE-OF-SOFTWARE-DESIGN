package com.cafepos;

import com.cafepos.common.Money;
import com.cafepos.pricing.DiscountPolicy;
import com.cafepos.pricing.FixedCouponDiscount;
import com.cafepos.pricing.LoyaltyPercentDiscount;
import com.cafepos.pricing.NoDiscount;
import com.cafepos.smells.OrderManagerGod;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DiscountPolicyCharacterizationTests {
    @Test void no_discount_returns_zero() {
        DiscountPolicy d = new NoDiscount();
        assertEquals(Money.zero(), d.discountOf(Money.of(20)));
    }

    @Test
    void loyalty_discount_percentage() {
        DiscountPolicy d = new LoyaltyPercentDiscount(5);
        assertEquals(Money.of(0.60), d.discountOf(Money.of(12)));
    }

    @Test
    void fixed_coupon_discount() {
        DiscountPolicy d = new FixedCouponDiscount(Money.of(1));
        assertEquals(Money.of(1.00), d.discountOf(Money.of(10.00)));
        // if subtotal smaller
        assertEquals(Money.of(1.00), d.discountOf(Money.of(1.00)));
    }

}
