package pl.training.payments;

import lombok.RequiredArgsConstructor;
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

    @Override
    public Payment process(PaymentRequest paymentRequest) {
        var payment = Payment.builder()
                .id(paymentIdGenerator.getNext())
                .value(paymentRequest.getValue())
                .status(PaymentStatus.STARTED)
                .timestamp(LocalDateTime.now())
                .build();
        new Thread(() -> {
                try {
                    Thread.sleep(3_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                payment.setStatus(PaymentStatus.CONFIRMED);
                var paymentTransferObject = new PaymentTransferObject();
                paymentTransferObject.setId(payment.getId());
                paymentTransferObject.setStatus(PaymentStatusTransferObject.CONFIRMED);
                paymentTransferObject.setTimestamp(payment.getTimestamp());
                paymentTransferObject.setValue(payment.getValue().toString());
                source.output().send(MessageBuilder.withPayload(paymentTransferObject).build());
        }).start();
        return paymentsRepository.saveAndFlush(payment);
    }

    @Override
    public Payment getPayment(String id) {
        return paymentsRepository.findById(id)
                .orElseThrow(PaymentNotFoundException::new);
    }

}
