import com.cafepos.domain.Order;
import com.cafepos.payment.PaymentStrategy;

public class FakePaymentStrategy implements PaymentStrategy {
    boolean call = false;

    @Override
    public void pay(Order order) {
        call = true;
    }

}
