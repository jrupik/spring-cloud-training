package pl.training.payments;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentsController implements PaymentsApi {

    private final PaymentsMapper paymentsMapper;
    private final PaymentsService paymentsService;
    private final UriBuilder uriBuilder = new UriBuilder();

    @Override
    public ResponseEntity<PaymentTransferObject> process(PaymentRequestTransferObject paymentRequestTransferObject) {
        var paymentRequest = paymentsMapper.toPaymentRequest(paymentRequestTransferObject);
        var payment = paymentsService.process(paymentRequest);
        var uri = uriBuilder.requestUriWithId(payment.getId());
        return ResponseEntity.created(uri)
                .body(paymentsMapper.toPaymentTransferObject(payment));
    }

    @Override
    public PaymentTransferObject getPayment(String id) {
        var payment = paymentsService.getPayment(id);
        return paymentsMapper.toPaymentTransferObject(payment);
    }

    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<Void> onPaymentNotFound(PaymentNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }

}
