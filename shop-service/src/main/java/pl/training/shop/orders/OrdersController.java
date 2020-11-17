package pl.training.shop.orders;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.training.shop.OrderTransferObject;
import pl.training.shop.OrdersApi;

@RestController
@RequiredArgsConstructor
public class OrdersController implements OrdersApi {

    private final OrdersMapper ordersMapper;
    private final OrdersService ordersService;

    @Override
    public ResponseEntity<Void> placeOrder(OrderTransferObject orderTransferObject) {
        var order = ordersMapper.toOrder(orderTransferObject);
        ordersService.placeOrder(order);
        return ResponseEntity.accepted().build();
    }

}
