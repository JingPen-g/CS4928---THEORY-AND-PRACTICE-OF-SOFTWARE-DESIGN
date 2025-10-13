import com.cafepos.domain.Order;
import com.cafepos.observer.OrderObserver;

public class FakeOrderObserver implements OrderObserver {

    boolean called = false;
    String eventType;

    @Override
    public void updated(Order order, String eventType) {
        called = true;
        this.eventType = eventType;
    }

}
