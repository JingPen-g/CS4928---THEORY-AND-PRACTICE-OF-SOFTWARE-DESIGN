import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public final class Order {
    private final long id;
    private final List<LineItem> items = new ArrayList<>();
    public Order(long id) { this.id = id; }
    public void addItem(LineItem li) {
        if (li.quantity() > 0) {
            items.add(li);
        }
    }
    public Money subtotal() {
        return
                items.stream().map(LineItem::lineTotal).reduce(Money.zero()
                        , Money::add);
    }
    public Money taxAtPercent(int percent) {
        Money base = subtotal();
        BigDecimal rate = BigDecimal.valueOf(percent).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        BigDecimal taxPay = base.getMoney().multiply(rate);
        double tax = taxPay.doubleValue();
        return Money.of(tax);
    }
    public Money totalWithTax(int percent) {
        Money taxPay = taxAtPercent(percent);
        Money subtotal = subtotal();
        return taxPay.add(subtotal);
    }

    public long getId() {
        return id;
    }

    public List<LineItem> getItems() {
        return items;
    }

    public void pay(PaymentStrategy strategy) {
        if (strategy == null) {
            throw new IllegalArgumentException("strategy required");
        }
        strategy.pay(this);
    }
}