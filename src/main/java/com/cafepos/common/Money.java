import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public final class Money implements Comparable<Money> {
    private final BigDecimal amount;
    public static Money of(double value) {
        return new Money(BigDecimal.valueOf(value));
    }
    public static Money zero() {
        return new Money(BigDecimal.ZERO);
    }
    private Money(BigDecimal a) {
        if (a == null) throw new IllegalArgumentException("amount required");
        this.amount = a.setScale(2, RoundingMode.HALF_UP);
    }
    public Money add(Money other) {
        Objects.requireNonNull(other,"adding amount required");
        return new Money(this.amount.add(other.amount));
    }
    public Money multiply(int qty) {
        return new Money(this.amount.multiply(BigDecimal.valueOf(qty)));
    }

    @Override
    public int compareTo(Money money) {
        return this.amount.compareTo(money.amount);
    }
    
    public BigDecimal getMoney() {
        return this.amount;
    }
// equals, hashCode, toString, etc.
    @Override
    public String toString() {
        return amount.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money)) return false;
        Money money = (Money) o;
        return amount.compareTo(money.amount) == 0; // compare numeric value
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount.stripTrailingZeros());
    }
}