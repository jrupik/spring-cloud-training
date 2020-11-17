package pl.training.shop.orders;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import pl.training.commons.FastMoneyMapper;
import pl.training.shop.IdTransferObject;
import pl.training.shop.OrderTransferObject;
import pl.training.shop.products.ProductsService;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {FastMoneyMapper.class})
public abstract class OrdersMapper {

    @Autowired
    private ProductsService productsService;

    public Order toOrder(OrderTransferObject orderTransferObject) {
        var products = orderTransferObject.getProducts().stream()
                .map(IdTransferObject::getId)
                .map(productsService::getById)
                .collect(Collectors.toList());
        return new Order(orderTransferObject.getClientId(), products);
    }

}
