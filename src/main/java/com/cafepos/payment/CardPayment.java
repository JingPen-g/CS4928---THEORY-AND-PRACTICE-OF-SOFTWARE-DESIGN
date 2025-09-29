public final class CardPayment implements PaymentStrategy {
    private final String cardNumber;
    public CardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    @Override
    public void pay(Order order) {
        // mask card and print payment confirmation
        String masked = cardNumber.substring(0, 12);
        String maskedCardNumber = cardNumber.replaceAll(masked, "*".repeat(12));
        System.out.println("[Card] Customer paid " + order.totalWithTax(10) + " EUR with card " + maskedCardNumber);
    }


}




