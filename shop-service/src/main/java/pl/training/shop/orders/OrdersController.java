package pl.training.shop.orders;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.training.commons.UriBuilder;
import pl.training.shop.OrderTransferObject;
import pl.training.shop.OrdersApi;

@RestController
@RequiredArgsConstructor
public class OrdersController implements OrdersApi {

    private final OrdersMapper ordersMapper;
    private final OrdersService ordersService;
    private final UriBuilder uriBuilder = new UriBuilder();

    @Override
    public ResponseEntity<Void> placeOrder(OrderTransferObject orderTransferObject) {
        var order = ordersMapper.toOrder(orderTransferObject);
        ordersService.placeOrder(order);
        var uri = uriBuilder.requestUriWithId(order.getId());
        return ResponseEntity.created(uri).build();
    }

    @Override
    public ResponseEntity<OrderTransferObject> getOrder(Long id) {
        var order = ordersService.getById(id);
        return ResponseEntity.ok(ordersMapper.toOrderTransferObject(order));
    }

}
