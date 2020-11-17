package pl.training.shop.payments;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.javamoney.moneta.FastMoney;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import pl.training.payments.PaymentRequestTransferObject;
import pl.training.payments.PaymentTransferObject;
import pl.training.payments.PaymentsApi;

import java.util.Optional;

@Transactional
//@Service
@Log
@RequiredArgsConstructor
public class FeignPaymentService implements PaymentsService {

    private final PaymentsApi paymentsApi;
    private final PaymentsMapper paymentsMapper;

    @Override
    public Optional<Payment> pay(FastMoney value) {
        var paymentRequestTransferObject = new PaymentRequestTransferObject(value.toString());
        try {
            var response = paymentsApi.process(paymentRequestTransferObject);
            return Optional.of(paymentsMapper.toPayment(response.getBody()));
        } catch (HttpClientErrorException | IllegalStateException exception) {
            log.warning("Payment failed: " + exception.getMessage());
        }
        return Optional.empty();
    }

}
