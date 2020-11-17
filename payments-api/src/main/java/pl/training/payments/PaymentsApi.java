package pl.training.payments;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient("payments-service")
@Validated
@RequestMapping(value = "payments", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
public interface PaymentsApi {

    @PostMapping
    ResponseEntity<PaymentTransferObject> process(@Valid @RequestBody PaymentRequestTransferObject paymentRequestTransferObject);

    @GetMapping("{id}")
    ResponseEntity<PaymentTransferObject> getPayment(@PathVariable String id);

}
