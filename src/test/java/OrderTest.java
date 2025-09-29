import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    @Test
    void order_totals() {
        var p1 = new SimpleProduct("A", "A", Money.of(2.50));
        var p2 = new SimpleProduct("B", "B", Money.of(3.50));
        var o = new Order(1);
        o.addItem(new LineItem(p1, 2));
        o.addItem(new LineItem(p2, 1));
        assertEquals(Money.of(8.50), o.subtotal());
        assertEquals(Money.of(0.85), o.taxAtPercent(10));
        assertEquals(Money.of(9.35), o.totalWithTax(10));
    }


    @Test void payment_strategy_called() {
        var p = new SimpleProduct("A", "A", Money.of(5));
        var order = new Order(42);
        order.addItem(new LineItem(p, 1));
        final boolean[] called = {false};
        PaymentStrategy fake = o -> called[0] = true;
        order.pay(fake);
        assertTrue(called[0], "Payment strategy should be called");
    }

    @Test void CardPayment() {
        CardPayment card = new CardPayment("1234567809876543");
        var p1 = new SimpleProduct("B", "B", Money.of(3.50));
        var o = new Order(1);
        o.addItem(new LineItem(p1, 2));
        card.pay(o);
    }

    @Test void CashPayment() {
        CashPayment cash = new CashPayment();
        var p1 = new SimpleProduct("B", "B", Money.of(1.50));
        var o = new Order(1);
        o.addItem(new LineItem(p1, 2));
        cash.pay(o);
    }

    @Test void WalletPayment() {
        WalletPayment wallet = new WalletPayment("123");
        var p1 = new SimpleProduct("B", "B", Money.of(1.00));
        var o = new Order(1);
        o.addItem(new LineItem(p1, 2));
        wallet.pay(o);
    }

}