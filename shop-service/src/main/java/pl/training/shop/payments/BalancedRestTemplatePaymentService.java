package pl.training.shop.payments;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.javamoney.moneta.FastMoney;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.training.payments.PaymentRequestTransferObject;
import pl.training.payments.PaymentTransferObject;

import java.util.Optional;

@Log
@Service
@RequiredArgsConstructor
public class BalancedRestTemplatePaymentService implements PaymentsService {

    private static final String PAYMENTS_RESOURCE_URI = "http://payments-service/payments";

    private final RestTemplate restTemplate;
    private final PaymentsMapper paymentsMapper;

    @Override
    public Optional<Payment> pay(FastMoney value) {
        var paymentRequestTransferObject = new PaymentRequestTransferObject(value.toString());
        try {
            var paymentTransferObject = restTemplate.postForObject(PAYMENTS_RESOURCE_URI, paymentRequestTransferObject, PaymentTransferObject.class);
            if (paymentTransferObject == null) {
                return Optional.empty();
            }
            return Optional.of(paymentsMapper.toPayment(paymentTransferObject));
        } catch (HttpClientErrorException | IllegalStateException exception) {
            log.warning("Payment failed: " + exception.getMessage());
        }
        return Optional.empty();
    }

}
