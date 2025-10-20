import com.cafepos.catalog.Product;
import com.cafepos.catalog.SimpleProduct;
import com.cafepos.common.Money;
import com.cafepos.decorator.Priced;
import com.cafepos.decorator.ExtraShot;
import com.cafepos.decorator.OatMilk;
import com.cafepos.decorator.SizeLarge;
import com.cafepos.domain.LineItem;
import com.cafepos.domain.Order;
import com.cafepos.factory.ProductFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FactoryManualChainingTest {
    @Test void drink_Test() {
        ProductFactory factory = new ProductFactory();
        Product product = factory.create("ESP+SHOT+OAT+L");
        Product manual = new SizeLarge(new OatMilk(new ExtraShot(new
                SimpleProduct("P-ESP","Espresso", Money.of(2.50)))));
        Money productMoney = product instanceof Priced p ? p.price() : product.price();
        Money manualMoney = manual instanceof Priced p ? p.price() : manual.price();

        Order order1 = new Order(5L);
        Order order2 = new Order(5L);
        order1.addItem(new LineItem(product, 1));
        order2.addItem(new LineItem(manual, 1));

        assertEquals(product.name(), manual.name());
        assertEquals(productMoney, manualMoney);
        assertEquals(order1.subtotal(), order2.subtotal());
        assertEquals(order1.totalWithTax(10), order2.totalWithTax(10));
    }



}
