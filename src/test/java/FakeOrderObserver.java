public class FakeOrderObserver implements OrderObserver{

    boolean called = false;
    String eventType;

    @Override
    public void updated(Order order, String eventType) {
        called = true;
        this.eventType = eventType;
    }
}
