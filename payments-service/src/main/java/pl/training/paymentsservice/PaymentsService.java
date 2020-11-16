package pl.training.paymentsservice;

public interface PaymentsService {

    Payment process(PaymentRequest paymentRequest);

    Payment getPayment(String id);

}
