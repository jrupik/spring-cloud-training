package pl.training.payments;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UUIDPaymentIdGenerator implements PaymentIdGenerator {

    @Override
    public String getNext() {
        return UUID.randomUUID().toString();
    }

}
