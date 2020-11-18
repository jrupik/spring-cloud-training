package pl.training.payments;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Transactional
@Service
@RequiredArgsConstructor
public class FakePaymentsService implements PaymentsService {

    private final PaymentIdGenerator paymentIdGenerator;
    private final PaymentsRepository paymentsRepository;
    private final Source source;
    private final PaymentsMapper paymentsMapper;

    @Override
    public Payment process(PaymentRequest paymentRequest) {
        var payment = Payment.builder()
                .id(paymentIdGenerator.getNext())
                .value(paymentRequest.getValue())
                .status(PaymentStatus.STARTED)
                .timestamp(LocalDateTime.now())
                .build();
        processPayment(payment);
        return paymentsRepository.saveAndFlush(payment);
    }

    @Override
    public Payment getPayment(String id) {
        return paymentsRepository.findById(id)
                .orElseThrow(PaymentNotFoundException::new);
    }

    private void processPayment(Payment payment) {
        new Thread(() -> {
            fakeDelay();
            payment.setStatus(PaymentStatus.CONFIRMED);
            paymentsRepository.saveAndFlush(payment);
            var paymentTransferObject = paymentsMapper.toPaymentTransferObject(payment);
            var message = MessageBuilder.withPayload(paymentTransferObject).build();
            source.output().send(message);
        }).start();
    }

    @SneakyThrows
    private void fakeDelay() {
        Thread.sleep(10_000);
    }

}
