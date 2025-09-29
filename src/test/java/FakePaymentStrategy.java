public class FakePaymentStrategy implements PaymentStrategy {
    boolean call = false;

    @Override
    public void pay(Order order) {
        call = true;
    }
}
