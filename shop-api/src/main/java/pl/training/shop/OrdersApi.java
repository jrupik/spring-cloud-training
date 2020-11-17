package pl.training.shop;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Validated
@RequestMapping(value = "orders", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
public interface OrdersApi {

    @PostMapping
    ResponseEntity<Void> placeOrder(@Valid @RequestBody OrderTransferObject orderTransferObject);

    @GetMapping("{id}")
    ResponseEntity<OrderTransferObject> getOrder(@PathVariable Long id);

}
