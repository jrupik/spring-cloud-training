package pl.training.shop.payments;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.javamoney.moneta.FastMoney;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.training.payments.PaymentRequestTransferObject;
import pl.training.payments.PaymentTransferObject;

import java.net.URI;
import java.util.Optional;

@Transactional
@Service
@Log
@RequiredArgsConstructor
public class RestTemplatePaymentService implements PaymentsService {

    private static final String PAYMENTS_SERVICE_NAME = "payments-service";
    private static final String PAYMENTS_RESOURCE = "/payments";
    private static final int PREFERRED_INSTANCE_INDEX = 0;

    private final DiscoveryClient discoveryClient;
    private final RestTemplate restTemplate;
    private final PaymentsMapper paymentsMapper;

    @Override
    public Optional<Payment> pay(FastMoney value) {
        var paymentRequestTransferObject = new PaymentRequestTransferObject(value.toString());
        try {
            var requestUri = getRequestUri();
            var paymentTransferObject = restTemplate.postForObject(requestUri, paymentRequestTransferObject, PaymentTransferObject.class);
            if (paymentTransferObject == null) {
                return Optional.empty();
            }
            return Optional.of(paymentsMapper.toPayment(paymentTransferObject));
        } catch (HttpClientErrorException | IllegalStateException exception) {
            log.warning("Payment failed: " + exception.getMessage());
        }
        return Optional.empty();
    }

    private URI getRequestUri() {
        var instances = discoveryClient.getInstances(PAYMENTS_SERVICE_NAME);
        if (instances.isEmpty()) {
            throw new IllegalStateException();
        }
        return instances.get(PREFERRED_INSTANCE_INDEX).getUri().resolve(PAYMENTS_RESOURCE);
    }

}
