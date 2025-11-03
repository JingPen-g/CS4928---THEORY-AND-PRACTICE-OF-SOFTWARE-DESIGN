package com.cafepos;

import com.cafepos.catalog.Product;
import com.cafepos.catalog.SimpleProduct;
import com.cafepos.common.Money;
import com.cafepos.decorator.Priced;
import com.cafepos.decorator.ExtraShot;
import com.cafepos.decorator.OatMilk;
import com.cafepos.decorator.SizeLarge;
import com.cafepos.decorator.Syrup;
import com.cafepos.domain.LineItem;
import com.cafepos.domain.Order;
import com.cafepos.factory.ProductFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class DecoratorTest {
    @Test
    void Stacked_decorator_ExtraShot_OatMilk_Test() {
        // Example usage
        Product espresso = new SimpleProduct("P-ESP", "Espresso", Money.of(2.50));
        Product decorated = new SizeLarge(new OatMilk(new ExtraShot(espresso)));

        System.out.println(decorated.name());
        System.out.println(decorated.price());

        assertEquals("Espresso + Extra Shot + Oat Milk (Large)", decorated.name());
        assertEquals(Money.of(4.50), decorated.price());


        // decorated.name() => "Espresso + Extra Shot + Oat Milk (Large)"
        // decorated.price() => 2.50 + 0.80 + 0.50 + 0.70 = 4.50
    }


    @Test
    void Stacked_decorator_ExtraShot_OatMilk_SizeLarge_Test() {
        // Example usage
        Product espresso = new SimpleProduct("P-ESP", "Espresso", Money.of(2.50));
        Product decorated = new SizeLarge(new OatMilk(new ExtraShot(espresso)));

        System.out.println(decorated.name());
        System.out.println(decorated.price());

        assertEquals("Espresso + Extra Shot + Oat Milk (Large)", decorated.name());
        assertEquals(Money.of(4.50), decorated.price());


        // decorated.name() => "Espresso + Extra Shot + Oat Milk (Large)"
        // decorated.price() => 2.50 + 0.80 + 0.50 + 0.70 = 4.50
    }

    @Test
    void Stacked_decorator_all_Test() {
        // Example usage
        Product espresso = new SimpleProduct("P-ESP", "Espresso", Money.of(2.50));
        Product decorated = new Syrup(new SizeLarge(new OatMilk(new ExtraShot(espresso))));

        System.out.println(decorated.name());
        System.out.println(decorated.price());

        assertEquals("Espresso + Extra Shot + Oat Milk (Large) + Syrup", decorated.name());
        assertEquals(Money.of(4.90), decorated.price());


        // decorated.name() => "Espresso + Extra Shot + Oat Milk (Large) + Syrup"
        // decorated.price() => 2.50 + 0.80 + 0.50 + 0.70 + 0.40 = 4.90
    }

    @Test void decorator_single_addon() {
        Product espresso = new SimpleProduct("P-ESP", "Espresso", Money.of(2.50));
        Product withShot = new ExtraShot(espresso);
        assertEquals("Espresso + Extra Shot", withShot.name());
        assertEquals(Money.of(3.30), ((Priced) withShot).price());
    }

    @Test void factory_parses_recipe() {
        ProductFactory f = new ProductFactory();
        Product p = f.create("ESP+SHOT+OAT");
        assertTrue(p.name().contains("Espresso") && p.name().contains("Oat Milk"));
    }

    @Test void order_uses_decorated_price() {
        Product espresso = new SimpleProduct("P-ESP", "Espresso", Money.of(2.50));
        Product withShot = new ExtraShot(espresso); // 3.30
        Order o = new Order(1);
        o.addItem(new LineItem(withShot, 2));
        assertEquals(Money.of(6.60), o.subtotal());
    }
}


