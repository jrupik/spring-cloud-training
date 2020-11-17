package pl.training.shop.orders;

import lombok.RequiredArgsConstructor;
import org.javamoney.moneta.FastMoney;
import org.springframework.stereotype.Service;
import pl.training.payments.PaymentRequestTransferObject;
import pl.training.shop.payments.Payment;
import pl.training.shop.payments.PaymentInitializationException;
import pl.training.shop.payments.PaymentsService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final PaymentsService paymentsService;
    private final OrderFee orderFee;

    public void placeOrder(Order order) {
        var paymentValue = order.getTotalValue().add(orderFee.getValue());
        var payment = paymentsService.pay(paymentValue)
                .orElseThrow(PaymentInitializationException::new);
        order.setPayment(payment);
        ordersRepository.saveAndFlush(order);
    }

    public Order getById(Long id) {
        return ordersRepository.findById(id)
                .orElseThrow(OrderNotFoundException::new);
    }

}
