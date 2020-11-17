package pl.training.shop.payments;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.javamoney.moneta.FastMoney;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.training.payments.PaymentRequestTransferObject;
import pl.training.payments.PaymentTransferObject;

import java.util.Optional;

@Primary
@Log
@Service
@RequiredArgsConstructor
public class BalancedRestTemplatePaymentService implements PaymentsService {

    private static final String PAYMENTS_RESOURCE_URI = "http://payments-service/payments";

    private final RestTemplate restTemplate;
    private final PaymentsMapper paymentsMapper;
    private final PaymentsRepository paymentsRepository;

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

    @StreamListener(Sink.INPUT)
    public void updatePaymentStatus(PaymentTransferObject paymentTransferObject) {
        log.info(paymentTransferObject.toString());
        var payment = paymentsMapper.toPayment(paymentTransferObject);
        paymentsRepository.saveAndFlush(payment);
    }

}
