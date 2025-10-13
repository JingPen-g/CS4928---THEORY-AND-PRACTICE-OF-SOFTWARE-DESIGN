import com.cafepos.common.Money;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTest {

    @Test
    void money_add() {
        Money money1 = Money.of(1.50);
        Money money2 = Money.of(2.50);

        Money total = money1.add(money2);
        assertEquals(Money.of(4.00), total);
    }

    @Test
    void money_multiply() {
        int qty = 3;
        Money money = Money.of(2.00);

        Money total = money.multiply(qty);
        assertEquals(Money.of(6.00), total);
    }


}