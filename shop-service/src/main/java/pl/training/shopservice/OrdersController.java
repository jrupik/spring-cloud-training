package pl.training.shopservice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.training.shop.OrderTransferObject;
import pl.training.shop.OrdersApi;

@RestController
@RequiredArgsConstructor
public class OrdersController implements OrdersApi {

    @Override
    public ResponseEntity<Void> placeOrder(OrderTransferObject orderTransferObject) {
        return null;
    }

}
