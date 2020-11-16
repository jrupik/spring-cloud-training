package pl.training.payments;

public interface PaymentsService {

    Payment process(PaymentRequest paymentRequest);

    Payment getPayment(String id);

}
