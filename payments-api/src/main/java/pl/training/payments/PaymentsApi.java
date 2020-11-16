package pl.training.payments;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(value = "payments", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
public interface PaymentsApi {

    @PostMapping
    ResponseEntity<PaymentTransferObject> process(@RequestBody PaymentRequestTransferObject paymentRequestTransferObject);

    @GetMapping("{id}")
    ResponseEntity<PaymentTransferObject> getPayment(@PathVariable String id);

}
